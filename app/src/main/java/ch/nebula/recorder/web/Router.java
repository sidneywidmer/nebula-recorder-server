package ch.nebula.recorder.web;

import ch.nebula.recorder.core.Component;
import io.javalin.Javalin;
import ch.nebula.recorder.web.controllers.AuthController;
import ch.nebula.recorder.web.controllers.UserController;

import javax.inject.Inject;

import static io.javalin.apibuilder.ApiBuilder.*;


public class Router implements Component {
    private final UserController userController;
    private final AuthController authController;

    @Inject
    public Router(
            UserController userController,
            AuthController authController
    ) {
        this.userController = userController;
        this.authController = authController;
    }

    @Override
    public void register(Javalin app) {
        app.routes(() -> {
            path("api/user/signup", () -> post(userController::signup));
            path("auth/login", () -> post(authController::login));
        });
    }
}
