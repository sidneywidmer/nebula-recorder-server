package ch.nebula.recorder.web;

import ch.nebula.recorder.core.Component;
import ch.nebula.recorder.web.controllers.AuthController;
import ch.nebula.recorder.web.controllers.UserController;
import io.javalin.Javalin;

import javax.inject.Inject;

import static ch.nebula.recorder.domain.auth.AuthRoles.AUTHENTICATED;
import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.core.security.SecurityUtil.roles;


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
            path("api/user/activate", () -> post(userController::activate));
            path("api/auth/login", () -> post(authController::login));
            path("api/auth/check", () -> get(authController::check, roles(AUTHENTICATED)));
        });
    }
}
