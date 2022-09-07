import com.google.gson.Gson;
import models.Book;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import wrappers.TestApiAppWrapper;

import java.io.IOException;

@Testcontainers
public class SimpleTestWithTestContainers {

    @Container
    public TestApiAppWrapper testApiApp = new TestApiAppWrapper();

    @Test
    public void test() throws IOException {
        HttpGet getRequest = new HttpGet("http://localhost:" + testApiApp.getFirstMappedPort() + "/books");
        getRequest.addHeader(new BasicHeader(HttpHeaders.ACCEPT, "application/json"));

        HttpResponse response = HttpClientBuilder.create().build().execute(getRequest);

        String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        Gson gson = new Gson();
        Book[] books = gson.fromJson(responseStr, Book[].class);

        assert books.length == 3;
    }
}
