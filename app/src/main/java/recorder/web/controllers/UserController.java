package recorder.web.controllers;

import io.javalin.http.Context;
import recorder.core.exceptions.ApiException;
import recorder.core.exceptions.SystemException;
import recorder.domain.services.UserService;
import recorder.domain.requests.UserSignupRequest;

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
