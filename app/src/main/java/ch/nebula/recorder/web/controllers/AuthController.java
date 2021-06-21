package ch.nebula.recorder.web.controllers;

import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.SystemException;
import ch.nebula.recorder.domain.models.User;
import ch.nebula.recorder.domain.requests.LoginRequest;
import ch.nebula.recorder.domain.services.AuthService;
import io.javalin.http.Context;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Validator;

import static io.javalin.plugin.rendering.template.TemplateUtil.model;


@Singleton
public class AuthController extends BaseController {
    private final AuthService authService;

    @Inject
    protected AuthController(AuthService authService, Validator validator) {
        super(validator);
        this.authService = authService;
    }

    public void login(Context ctx) throws ApiException, SystemException {
        var userLogin = (LoginRequest) this.validateJson(ctx, LoginRequest.class);
        var token = this.authService.generateToken(userLogin);

        ctx.json(model("token", token));
    }

    public void check(Context ctx) {
        User user = ctx.attribute("user");
        ctx.json(model("success", true, "user", user.getId()));
    }
}
