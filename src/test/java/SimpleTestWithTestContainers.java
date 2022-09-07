import models.Book;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import utils.HttpClient;
import utils.RetrieveUtil;
import wrappers.ContainersLoader;
import wrappers.MongoContainerWrapper;
import wrappers.TestApiAppWrapper;

import java.io.IOException;

@Testcontainers
public class SimpleTestWithTestContainers {

    private static MongoContainerWrapper mongoContainer;
    private static TestApiAppWrapper testApiAppContainer;

    @BeforeAll
    public static void beforeAll() {
        mongoContainer = ContainersLoader.bootMongo();
        testApiAppContainer = ContainersLoader.bootTestApiApp();
    }

    @Test
    public void test() throws IOException {
        HttpResponse response = HttpClient.get(testApiAppContainer.getBooksUrl());
        Book[] books = RetrieveUtil.retrieveResourceFromResponse(response, Book[].class);

        assert books.length == 3;
    }
}
