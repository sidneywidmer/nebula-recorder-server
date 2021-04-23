package recorder;

import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;
import recorder.web.Router;

import javax.inject.Inject;

public class Rocket {

    private final Javalin app;
    private final Dotenv dotenv;
    private Router router;

    @Inject
    public Rocket(
            Javalin app,
            Dotenv dotenv,
            Router router
    ) {
        this.app = app;
        this.dotenv = dotenv;
        this.router = router;
    }

    public void launch() {
        var port = Integer.parseInt(dotenv.get("SERVER_PORT"));

        app.start(port);
        router.register(app);
    }
}
