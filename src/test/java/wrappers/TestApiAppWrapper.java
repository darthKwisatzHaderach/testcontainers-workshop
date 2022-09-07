package wrappers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.images.builder.dockerfile.DockerfileBuilder;

public class TestApiAppWrapper  extends GenericContainer<TestApiAppWrapper> {
    public static final String SERVICE_NAME = "test-api-app";

    public TestApiAppWrapper(Network network) {
        super(buildImage());

        this.withNetwork(network);
        this.withExposedPorts(8080);
        this.withCreateContainerCmdModifier(cmd -> cmd.withName(SERVICE_NAME));
        this.waitingFor(
                Wait.forLogMessage(".*Started ApiApplication in .* seconds.*", 1)
        );
    }

    private static ImageFromDockerfile buildImage() {
        String imageName = "arm64v8/openjdk:18-jdk";
        String jarFileName = "api-mongo.jar";

        ImageFromDockerfile imageFromDockerfile = new ImageFromDockerfile(imageName)
                .withFileFromString("Dockerfile", buildDockerfile(imageName, jarFileName))
                .withFileFromClasspath(jarFileName, jarFileName);

        return imageFromDockerfile;
    }

    private static String buildDockerfile(String imageName, String jarFileName) {
        DockerfileBuilder builder = new DockerfileBuilder();

        builder
                .from(imageName)
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