package module_13.Training;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupTest {
    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("https://bt.rozetka.com.ua/ua/philips-hu2510-10/p354228279/")
                .get();
        System.out.println("document.body().text() = " + document.body().text());
    }
}
