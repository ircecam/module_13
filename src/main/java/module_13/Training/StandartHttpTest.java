package module_13.Training;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StandartHttpTest {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        String uri = "https://ru.wikipedia.org/wiki/HTTP";
        //String uri =  "https://api.monobank.ua/bank/currency";
        HttpRequest httpRequest = HttpRequest.newBuilder( new URI(uri))
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("httpResponse.statusCode() = " + httpResponse.statusCode());
        System.out.println("httpResponse.body() = " + httpResponse.body());
    }
}
