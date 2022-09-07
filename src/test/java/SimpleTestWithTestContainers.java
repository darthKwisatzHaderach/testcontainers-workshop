import com.google.gson.Gson;
import models.Book;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Testcontainers;
import wrappers.MongoContainerWrapper;
import wrappers.TestApiAppWrapper;

import java.io.IOException;

@Testcontainers
public class SimpleTestWithTestContainers {

    private static TestApiAppWrapper testApiAppContainer;
    private static MongoContainerWrapper mongoContainer;

    @BeforeAll
    public static void beforeAll() {
        Network network = Network.newNetwork();

        mongoContainer = new MongoContainerWrapper(network);
        mongoContainer.start();

        testApiAppContainer = new TestApiAppWrapper(network);
        testApiAppContainer.start();
    }

    @Test
    public void test() throws IOException {
        HttpGet getRequest = new HttpGet("http://localhost:" + testApiAppContainer.getFirstMappedPort() + "/books");
        getRequest.addHeader(new BasicHeader(HttpHeaders.ACCEPT, "application/json"));
        System.out.println(getRequest);

        HttpResponse response = HttpClientBuilder.create().build().execute(getRequest);

        String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(responseStr);
        Gson gson = new Gson();
        Book[] books = gson.fromJson(responseStr, Book[].class);

        assert books.length == 3;
    }
}
