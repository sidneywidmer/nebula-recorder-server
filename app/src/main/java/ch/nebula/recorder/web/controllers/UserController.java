package ch.nebula.recorder.web.controllers;

import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.SystemException;
import ch.nebula.recorder.domain.requests.UserSignupRequest;
import io.javalin.http.Context;
import ch.nebula.recorder.domain.services.UserService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Validator;


@Singleton
public class UserController extends BaseController {
    private final UserService userService;

    @Inject
    protected UserController(UserService userService, Validator validator) {
        super(validator);
        this.userService = userService;
    }

    public void signup(Context ctx) throws ApiException, SystemException {
        var userSignup = (UserSignupRequest) this.validate(ctx, UserSignupRequest.class);
        this.userService.create(userSignup);

        ctx.status(200);
    }
}
