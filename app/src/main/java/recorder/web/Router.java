package recorder.web;

import io.javalin.Javalin;
import recorder.web.controllers.WelcomeController;

import javax.inject.Inject;

import static io.javalin.apibuilder.ApiBuilder.*;


public class Router {

    private final WelcomeController welcomeController;

    @Inject
    public Router(WelcomeController welcomeController) {
        this.welcomeController = welcomeController;
    }

    public void register(Javalin app) {
        app.routes(() -> {
            path("/", () -> {
                get(welcomeController::index);
            });
        });
    }
}
