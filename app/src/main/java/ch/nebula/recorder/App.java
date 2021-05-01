package ch.nebula.recorder;

import ch.nebula.recorder.providers.Bootstrap;
import ch.nebula.recorder.web.Router;
import com.google.inject.Guice;
import com.typesafe.config.Config;
import io.javalin.Javalin;
import ch.nebula.recorder.web.ErrorHandler;

import javax.inject.Inject;

public class App {
    private final Javalin app;
    private final Config config;
    private final Router router;
    private final ErrorHandler errorHandler;

    @Inject
    public App(
            Javalin app,
            Config config,
            Router router,
            ErrorHandler errorHandler
    ) {
        this.app = app;
        this.config = config;
        this.router = router;
        this.errorHandler = errorHandler;
    }

    public void start() {
        router.register(app);
        errorHandler.register(app);

        app.start(config.getInt("server.port"));
    }

    public static void main(String[] args) {
        // Initialize DI
        var injector = Guice.createInjector(new Bootstrap());

        // 3..2..1.. LIFTOFF!
        injector.getInstance(App.class).start();
    }
}
