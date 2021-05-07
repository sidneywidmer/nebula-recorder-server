package ch.nebula.recorder.providers;

import com.typesafe.config.Config;
import io.javalin.Javalin;

import javax.inject.Inject;
import javax.inject.Provider;

public class JavalinProvider implements Provider<Javalin> {
    private final Config config;

    @Inject
    public JavalinProvider(Config config) {
        this.config = config;
    }

    /**
     * New javalin instance with a custom template engine. Additional javalin configuration can be
     * done within the create method.
     */
    @Override
    public Javalin get() {
        return Javalin.create(cfg -> {
            cfg.addStaticFiles("public");
            cfg.enforceSsl = config.getBoolean("server.force-ssl");
        });
    }
}
