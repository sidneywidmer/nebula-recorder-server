package recorder;

import com.typesafe.config.Config;
import io.javalin.Javalin;
import recorder.web.Router;

import javax.inject.Inject;

public class Rocket {

    private final Javalin app;
    private final Config config;
    private final Router router;

    @Inject
    public Rocket(
            Javalin app,
            Config config,
            Router router
    ) {
        this.app = app;
        this.config = config;
        this.router = router;
    }

    public void launch() {
        app.start(config.getInt("server.port"));
        router.register(app);
    }
}
