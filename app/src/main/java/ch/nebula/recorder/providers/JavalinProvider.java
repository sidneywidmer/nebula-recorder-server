package ch.nebula.recorder.providers;

import com.typesafe.config.Config;
import ch.nebula.recorder.domain.auth.JWTAccessManager;
import io.javalin.Javalin;
import io.javalin.core.util.Util;
import io.javalin.http.staticfiles.Location;

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
        var recordingsPath = config.getString("storage.recordings-path");

        return Javalin.create(cfg -> {
            // Serves the FE
            cfg.addStaticFiles("public");

            // Serves the uploaded recordings
            cfg.addStaticFiles("/recordings", recordingsPath, Location.EXTERNAL);

            // Depending on given config, enforce ssl
            cfg.enforceSsl = config.getBoolean("server.force-ssl");

            // Set the class the handles all the JWT Auth logic
            cfg.accessManager(accessManager);

            // CORS needs to be enabled for our FE to have access on the different APIs
            cfg.enableCorsForAllOrigins();
        });
    }
}
