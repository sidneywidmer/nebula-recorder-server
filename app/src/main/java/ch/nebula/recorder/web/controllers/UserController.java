package ch.nebula.recorder.web.controllers;

import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.SystemException;
import ch.nebula.recorder.domain.requests.UserSignupRequest;
import ch.nebula.recorder.domain.services.MailService;
import ch.nebula.recorder.domain.services.UserService;
import io.javalin.http.Context;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Validator;


@Singleton
public class UserController extends BaseController {
    private final UserService userService;
    private final MailService mailService;

    @Inject
    protected UserController(UserService userService, Validator validator, MailService mailService) {
        super(validator);
        this.userService = userService;
        this.mailService = mailService;
    }

    public void signup(Context ctx) throws ApiException, SystemException {
        var userSignup = (UserSignupRequest) this.validate(ctx, UserSignupRequest.class);
        this.userService.create(userSignup);
        this.mailService.send();

        ctx.status(200);
    }
}
