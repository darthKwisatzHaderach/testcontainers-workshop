package wrappers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.images.builder.dockerfile.DockerfileBuilder;

import java.util.Map;

import static java.util.Map.entry;

public class TestApiAppWrapper extends GenericContainer<TestApiAppWrapper> {
    public static final String SERVICE_NAME = "test-api-app";

    public TestApiAppWrapper(Network network, String datasourceUrl) {
        super(buildImage(Map.ofEntries(entry("spring.datasource.url", datasourceUrl))));

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

    private static ImageFromDockerfile buildImage(Map env) {
        String imageName = "arm64v8/openjdk:18-jdk";
        String jarFileName = "api-mongo.jar";

        ImageFromDockerfile imageFromDockerfile = new ImageFromDockerfile(imageName)
                .withFileFromString("Dockerfile", buildDockerfile(imageName, env, jarFileName))
                .withFileFromClasspath(jarFileName, jarFileName);

        return imageFromDockerfile;
    }

    private static String buildDockerfile(String imageName, Map env, String jarFileName) {
        DockerfileBuilder builder = new DockerfileBuilder();

        builder
                .from(imageName)
                .env(env)
                .copy(jarFileName, jarFileName)
                .entryPoint("java", "-jar", "/" + jarFileName);

        return builder.build();
    }

}

/*

FROM arm64v8/openjdk:11-jdk
COPY api.jar api.jar
ENTRYPOINT ["java","-jar","/api.jar"]

*/