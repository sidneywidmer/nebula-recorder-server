package ch.nebula.recorder.providers;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.typesafe.config.Config;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.template.JavalinPebble;

import javax.inject.Inject;
import javax.inject.Provider;

public class JavalinProvider implements Provider<Javalin> {
    private final PebbleEngine engine;
    private final Config config;

    @Inject
    public JavalinProvider(PebbleEngine engine, Config config) {
        this.engine = engine;
        this.config = config;
    }

    /**
     * New javalin instance with a custom template engine. Additional javalin configuration can be
     * done within the create method.
     */
    @Override
    public Javalin get() {
        JavalinPebble.configure(engine);

        return Javalin.create(cfg -> {
            cfg.addStaticFiles("static");
            cfg.enforceSsl = config.getBoolean("server.force-ssl");
        });
    }
}
