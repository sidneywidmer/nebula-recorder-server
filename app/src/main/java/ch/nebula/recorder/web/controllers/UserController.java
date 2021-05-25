package ch.nebula.recorder.web.controllers;

import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.core.exceptions.SystemException;
import ch.nebula.recorder.domain.models.User;
import ch.nebula.recorder.domain.requests.UserActivateRequest;
import ch.nebula.recorder.domain.requests.UserSignupRequest;
import ch.nebula.recorder.domain.services.MailService;
import ch.nebula.recorder.domain.services.UserService;
import io.javalin.http.Context;
import net.sargue.mailgun.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Validator;
import java.util.Map;


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
        User user = this.userService.create(userSignup);

        //move into user.create
        String activationCode = this.mailService.generateActivationCode();

        // move error handling into mailservice
        Response response = this.mailService.sendActivationMail(user, activationCode);
        if (!response.isOk()) {
            throw new InvalidDataException(Map.of("_", "Activation mail not sent"));
        }

        //remove because its not longer neceseeary
        user.setActivationCode(activationCode); //TODO: ask sidney, hash activationCode?
        user.update();

        ctx.status(200);
    }

    public void activate(Context ctx) throws ApiException, SystemException {
        UserActivateRequest userActivation = this.validate(ctx, UserActivateRequest.class);
        this.userService.activate(userActivation);

        ctx.status(200);
    }
}
