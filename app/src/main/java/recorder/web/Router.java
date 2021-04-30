package recorder.web;

import io.javalin.Javalin;
import recorder.web.controllers.AuthController;
import recorder.web.controllers.UserController;
import recorder.web.controllers.WelcomeController;

import javax.inject.Inject;

import static io.javalin.apibuilder.ApiBuilder.*;


public class Router {
    private final WelcomeController welcomeController;
    private final UserController userController;
    private final AuthController authController;

    @Inject
    public Router(
            WelcomeController welcomeController,
            UserController userController,
            AuthController authController
    ) {
        this.welcomeController = welcomeController;
        this.userController = userController;
        this.authController = authController;
    }

    public void register(Javalin app) {
        app.routes(() -> {
            path("/", () -> get(welcomeController::index));
            path("api/user/signup", () -> post(userController::signup));
            path("auth/login", () -> post(authController::login));
        });
    }
}
