package ch.nebula.recorder.providers;

import com.auth0.jwt.JWTVerifier;
import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import io.ebean.Database;
import io.javalin.Javalin;

import javax.validation.Validator;


public class Bootstrap extends AbstractModule {
    /**
     * Register all our providers.
     */
    protected void configure() {
        bind(Config.class).toProvider(ConfigProvider.class).asEagerSingleton();
        bind(Database.class).toProvider(DbProvider.class).asEagerSingleton();
        bind(Validator.class).toProvider(ValidationProvider.class).asEagerSingleton();
        bind(JWTVerifier.class).toProvider(JWTProvider.class).asEagerSingleton();
        bind(Javalin.class).toProvider(JavalinProvider.class).asEagerSingleton();
    }
}
