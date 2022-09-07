package utils;

import com.github.dockerjava.api.model.ContainerNetwork;
import org.testcontainers.containers.GenericContainer;

import java.util.Objects;

public class NetworkUtils {
    public static <T extends GenericContainer<T>> String getVirtualNetworkIpAddress(GenericContainer<T> container) {
        final var networkId = container.getNetwork().getId();
        final var networks = container
                .getContainerInfo()
                .getNetworkSettings()
                .getNetworks()
                .values();

        return networks.stream().filter(n -> Objects.equals(n.getNetworkID(), networkId))
                .findAny()
                .map(ContainerNetwork::getIpAddress)
                .orElse("127.0.0.1");
    }
}
