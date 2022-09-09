package wrappers;

import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.images.builder.dockerfile.DockerfileBuilder;

import java.util.Map;

public class DockerImageBuilder {

    public static ImageFromDockerfile buildImage(Map env, String jarFileName) {
        String imageName = "arm64v8/openjdk:18-jdk";

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
