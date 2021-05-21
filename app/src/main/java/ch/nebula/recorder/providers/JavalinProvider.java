package ch.nebula.recorder.providers;

import com.typesafe.config.Config;
import ch.nebula.recorder.domain.auth.JWTAccessManager;
import io.javalin.Javalin;

import javax.inject.Inject;
import javax.inject.Provider;

public class JavalinProvider implements Provider<Javalin> {
    private final Config config;
    private final JWTAccessManager accessManager;

    @Inject
    public JavalinProvider(Config config, JWTAccessManager accessManager) {
        this.config = config;
        this.accessManager = accessManager;
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
            cfg.accessManager(accessManager);
            cfg.enableCorsForAllOrigins();
        });
    }
}
