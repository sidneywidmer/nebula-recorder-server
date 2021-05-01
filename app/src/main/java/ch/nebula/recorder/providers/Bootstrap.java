package ch.nebula.recorder.providers;

import com.google.inject.AbstractModule;
import com.mitchellbosecke.pebble.PebbleEngine;
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
        bind(PebbleEngine.class).toProvider(TemplateProvider.class).asEagerSingleton();
        bind(Validator.class).toProvider(ValidationProvider.class).asEagerSingleton();
        bind(Javalin.class).toProvider(JavalinProvider.class).asEagerSingleton();
    }
}
