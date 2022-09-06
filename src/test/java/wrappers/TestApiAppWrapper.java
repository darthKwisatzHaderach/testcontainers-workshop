package wrappers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.images.builder.dockerfile.DockerfileBuilder;

public class TestApiAppWrapper  extends GenericContainer<TestApiAppWrapper> {
    public static final String SERVICE_NAME = "test-api-app";

    private static ImageFromDockerfile buildImage(String serviceName) {
        String imageName = "arm64v8/openjdk:11-jdk";
        String jarFileName = "api.jar";

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