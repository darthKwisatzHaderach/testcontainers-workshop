package wrappers;

import org.testcontainers.containers.Network;

public class ContainersLoader {

    public static Network getNetwork() {
        return LazyNetworkLoader.INSTANCE;
    }

    public static MongoContainerWrapper bootMongo() {
        return LazyMongoLoader.INSTANCE;
    }

    public static TestApiAppWrapper bootTestApiApp() {
        return LazyTestApiAppLoader.INSTANCE;
    }

    private static class LazyNetworkLoader {
        private static final Network INSTANCE = Network.newNetwork();
    }

    private static class LazyMongoLoader {
        private static final MongoContainerWrapper INSTANCE;

        static {
            INSTANCE = new MongoContainerWrapper(getNetwork());
            INSTANCE.start();
        }
    }

    private static class LazyTestApiAppLoader {
        private static final TestApiAppWrapper INSTANCE;

        static {
            INSTANCE = new TestApiAppWrapper(getNetwork(), bootMongo().getInternalUri());
            INSTANCE.start();
        }
    }
}
