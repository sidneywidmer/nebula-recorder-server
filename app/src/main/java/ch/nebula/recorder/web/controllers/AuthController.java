package ch.nebula.recorder.web.controllers;

import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.SystemException;
import ch.nebula.recorder.domain.requests.LoginRequest;
import ch.nebula.recorder.domain.services.AuthService;
import io.javalin.http.Context;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Validator;


@Singleton
public class AuthController extends BaseController {
    private final AuthService authService;

    @Inject
    protected AuthController(AuthService authService, Validator validator) {
        super(validator);
        this.authService = authService;
    }

    public void login(Context ctx) throws ApiException, SystemException {
        var userLogin = (LoginRequest) this.validate(ctx, LoginRequest.class);
        var token = this.authService.generateToken(userLogin);

        ctx.status(200);
    }
}
