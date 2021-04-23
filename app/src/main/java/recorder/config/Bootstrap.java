package recorder.config;

import com.google.inject.AbstractModule;
import io.ebean.Database;

public class Bootstrap extends AbstractModule {
    protected void configure() {
        bind(Database.class).toProvider(DbProvider.class).asEagerSingleton();

        install(new EnvConfig());
        install(new JavalinConfig());
    }
}
