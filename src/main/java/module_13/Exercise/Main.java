package module_13.Exercise;


import com.google.gson.Gson;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static final String DEFAULT_USER_JSON = "{\n" +
            "    \"id\": 11,\n" +
            "    \"name\": \"Bibik Go\",\n" +
            "    \"username\": \"Bret\",\n" +
            "    \"email\": \"Sincere@april.biz\",\n" +
            "    \"address\": {\n" +
            "      \"street\": \"Kulas Light\",\n" +
            "      \"suite\": \"Apt. 556\",\n" +
            "      \"city\": \"Gwenborough\",\n" +
            "      \"zipcode\": \"92998-3874\",\n" +
            "      \"geo\": {\n" +
            "        \"lat\": \"-37.3159\",\n" +
            "        \"lng\": \"81.1496\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"phone\": \"1-770-736-8031 x56442\",\n" +
            "    \"website\": \"hildegard.org\",\n" +
            "    \"company\": {\n" +
            "      \"name\": \"Romaguera-Crona\",\n" +
            "      \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
            "      \"bs\": \"harness real-time e-markets\"\n" +
            "    }\n" +
            "  }";
    public static final String URI = "https://jsonplaceholder.typicode.com/users";
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, ParseException {

        User defaultUser = new Gson().fromJson(DEFAULT_USER_JSON,User.class);

        System.out.println("HttpUtil.addNewUser(defaultUser,uri) = " + HttpUtil.addNewUser(defaultUser, URI));
        HttpUtil.updateUser(DEFAULT_USER_JSON,10,URI);
        HttpUtil.deleteUser(10,URI);
        HttpUtil.getUserById(7,URI);
        HttpUtil.getInfoUser(URI);
        HttpUtil.getUserByName("Bret",URI);
        HttpUtil.getComments(1,URI);
    }
}
