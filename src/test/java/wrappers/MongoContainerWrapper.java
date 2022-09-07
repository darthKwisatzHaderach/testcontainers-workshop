package wrappers;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.utility.DockerImageName;

import static utils.NetworkUtils.getVirtualNetworkIpAddress;

public class MongoContainerWrapper extends MongoDBContainer {
    public static final String MONGODB_VERSION = "mongo:4.4.10";
    public static final String SERVICE_NAME = "mongo";

    public MongoContainerWrapper(Network network) {
        super(DockerImageName.parse(MONGODB_VERSION));

        withNetwork(network);
        withCreateContainerCmdModifier(cmd -> cmd.withName(SERVICE_NAME));
    }

    public String getInternalUri() {
        return "mongodb://" + getHostAndPort();
    }

    public String getHostAndPort() {
        return getVirtualNetworkIpAddress(this) + ":27017";
    }
}