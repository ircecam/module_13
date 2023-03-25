package module_13.Exercise;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.parser.ParseException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HttpUtil {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();


    //Единственный метод, который будет возврашать User, в дальнейшем буду выводить данные в самом теле метода для удобства проверки
    public static User addNewUser(User user, String uri) throws URISyntaxException, IOException, InterruptedException {
        String json = new Gson().toJson(user);
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(uri))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = response(httpRequest);

        User newUser = new Gson().fromJson(response.body(),User.class);
        return newUser;

    }
    public static void updateUser(String json, int idUser, String uri) throws URISyntaxException, IOException, InterruptedException {
        String newUri = uri + "/{0}";
        String formattedLink = MessageFormat
                .format(newUri, idUser);
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(formattedLink))
                .headers("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse response = response(httpRequest);

        String newJson = new Gson().toJson(response.body());
        System.out.println(newJson);
    }
    public static void deleteUser(int idUser, String uri) throws URISyntaxException, IOException, InterruptedException {
        String newUri = uri + "/{0}";
        String formattedLink = MessageFormat
                .format(newUri, idUser);
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(formattedLink))
                .headers("Content-Type", "application/json")
                .DELETE()
                .build();
        HttpResponse response = CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println("response.statusCode() = " + response.statusCode());
    }
    public static void getInfoUser(String uri) throws URISyntaxException, IOException, InterruptedException {
        HttpResponse response = response(requestGET(uri));
        System.out.println("response.body() = " + response.body());

    }
    public static void getUserById(int userId,String uri) throws URISyntaxException, IOException, InterruptedException {
        String newUri = uri + "/{0}";
        String formattedLink = MessageFormat
                .format(newUri, userId);

        HttpResponse response = response(requestGET(formattedLink));

        System.out.println("response.body() = " + response.body());
    }
    public static void getUserByName(String name, String uri) throws URISyntaxException, IOException, InterruptedException {
        String newUri = uri + "?username={0}";
        String formattedLink = MessageFormat
                .format(newUri, name);
        HttpResponse response = response(requestGET(formattedLink));

        System.out.println("response.body() = " + response.body());
    }
    public static void getOpenTasks(String uri, int userID) throws URISyntaxException, IOException, InterruptedException {
        String newUri = uri + "/{0}/todos";
        String formattedLink = MessageFormat
                .format(newUri, userID);

        HttpResponse response = response(requestGET(formattedLink));
        Type secListType = new TypeToken<ArrayList<Comment>>(){}.getType();
        ArrayList<Comment> comments = new Gson().fromJson(response.body().toString(), secListType);
        List<Comment> commentsSorted = comments.stream()
                .filter(comment -> comment.isCompleted() == false)
                .collect(Collectors.toList());
        System.out.println(commentsSorted);
    }
    public static void getComments(int userId,String uri) throws URISyntaxException, IOException, InterruptedException, ParseException {
        String newUri = uri + "/{0}/posts";
        String formattedLink = MessageFormat
                .format(newUri, userId);
        HttpResponse response = response(requestGET(formattedLink));

        Type secListType = new TypeToken<ArrayList<Post>>(){}.getType();
        ArrayList<Post> posts = new Gson().fromJson(response.body().toString(), secListType);

        List<Integer> collect = posts.stream().map(item -> item.getId()).collect(Collectors.toList());
        Integer maxId = collect.stream().max(Comparator.naturalOrder()).get();

        String formattedLink1 = MessageFormat
                .format("https://jsonplaceholder.typicode.com/posts/{0}/comments", maxId);

        System.out.println("response(requestGET(formattedLink1)).body() = " + response(requestGET(formattedLink1)).body());
        setToFile(response(requestGET(formattedLink1)).body(),userId,maxId);
    }

    private  static boolean setToFile(String text, int userId, int maxId){
        String formattedLink = MessageFormat.format("user-%d-post-%d-comments.json",userId,maxId);
        try(OutputStream fos = new FileOutputStream(formattedLink)){
            fos.write(text.getBytes(StandardCharsets.UTF_8));
            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpRequest requestGET(String formattedLink) throws URISyntaxException {
        HttpRequest httpRequest = HttpRequest.newBuilder(new URI(formattedLink))
                .headers("Content-Type", "application/json")
                .GET()
                .build();
        return httpRequest;
    }

    private static HttpResponse<String> response(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
        return response;
    }
}
