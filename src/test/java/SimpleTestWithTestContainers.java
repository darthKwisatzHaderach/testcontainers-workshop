import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class SimpleTestWithTestContainers {

    @Container
    public GenericContainer mongoDb = new GenericContainer(DockerImageName.parse("mongo:4.0.10")).withExposedPorts(27017);

    @Test
    public void test() {
        MongoClient client = MongoClients.create("mongodb://localhost:" + mongoDb.getFirstMappedPort());
        client.listDatabaseNames().forEach(System.out::println);
    }
}
