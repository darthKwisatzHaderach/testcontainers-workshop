package wrappers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.Map;

import static java.util.Map.entry;

public class TestApiAppWrapper extends GenericContainer<TestApiAppWrapper> {
    private static final String SERVICE_NAME = "test-api-app";
    private static String jarFileName = "api-mongo.jar";

    public TestApiAppWrapper(Network network, String datasourceUrl) {
        super(DockerImageBuilder.buildImage(Map.ofEntries(entry("spring.datasource.url", datasourceUrl)), jarFileName));

        this.withNetwork(network);
        this.withExposedPorts(8080);
        this.withCreateContainerCmdModifier(cmd -> cmd.withName(SERVICE_NAME));
        this.waitingFor(
                Wait.forLogMessage(".*Started ApiApplication in .* seconds.*", 1)
        );
    }

    public String getBooksUrl() {
        return "http://localhost:" + this.getFirstMappedPort() + "/books";
    }
}

/*

FROM arm64v8/openjdk:11-jdk
COPY api.jar api.jar
ENTRYPOINT ["java","-jar","/api.jar"]

*/