package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.BaseTest;
import ch.nebula.recorder.core.Generator;
import ch.nebula.recorder.core.Hasher;
import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.domain.models.User;
import ch.nebula.recorder.domain.models.query.QUser;
import ch.nebula.recorder.domain.requests.UserActivateRequest;
import ch.nebula.recorder.domain.requests.UserSignupRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Base64;

public class UserServiceTest extends BaseTest {
    private final UserService service;

    public UserServiceTest() {
        service = new UserService(new Hasher(), new Generator());
    }

    @Test
    public void givenNewUser_testCreateHappyPath() throws ApiException {
        var request = new UserSignupRequest();
        request.setEmail("foo@bar.ch");
        request.setPassword("hunter123");

        var user = service.create(request);

        assert (request.getEmail().equals(user.getEmail()));
    }

    @Test
    public void givenExistingUser_testCreateThrowsUserAlreadyExists() throws ApiException {
        var request = new UserSignupRequest();
        request.setEmail("foo@bar.ch");
        request.setPassword("hunter123");

        service.create(request);

        var exception = Assertions.assertThrows(InvalidDataException.class, () -> service.create(request));
        assert ((exception.getMessages().get("_").contains("User already exists")));
    }

    @Test
    public void givenActivationRequest_testActivateHappyPath() throws ApiException {
        var newUser = new User("foo@bar.ch", "hunter123");
        newUser.setActivationCode("ABC");
        newUser.save();

        var request = new UserActivateRequest();
        request.setEmail(Base64.getEncoder().encodeToString("foo@bar.ch".getBytes()));
        request.setActivationCode(Base64.getEncoder().encodeToString("ABC".getBytes()));

        service.activate(request);
        var user = new QUser().email.equalTo("foo@bar.ch").findOne();
        assert ((user.getWhenActivated() != null));
    }

    @Test
    public void givenActivationRequest_testActivateThrowsUserDoesntExist() throws ApiException {
        var request = new UserActivateRequest();
        request.setEmail(Base64.getEncoder().encodeToString("foo@bar.ch".getBytes()));
        request.setActivationCode(Base64.getEncoder().encodeToString("ABC".getBytes()));

        var exception = Assertions.assertThrows(InvalidDataException.class, () -> service.activate(request));
        assert ((exception.getMessages().get("_").contains("User doesnt exist")));
    }

    @Test
    public void givenActivationRequest_testActivateThrowsUserAlreadyActivated() throws ApiException {
        var newUser = new User("foo@bar.ch", "hunter123");
        newUser.setActivationCode("ABC");
        newUser.setWhenActivated(Instant.now());
        newUser.save();

        var request = new UserActivateRequest();
        request.setEmail(Base64.getEncoder().encodeToString("foo@bar.ch".getBytes()));
        request.setActivationCode(Base64.getEncoder().encodeToString("ABC".getBytes()));

        var exception = Assertions.assertThrows(InvalidDataException.class, () -> service.activate(request));
        assert ((exception.getMessages().get("_").contains("User already activated")));
    }

    @Test
    public void givenActivationRequest_testActivateThrowsWrongActivationCode() throws ApiException {
        var newUser = new User("foo@bar.ch", "hunter123");
        newUser.setActivationCode("ABC");
        newUser.save();

        var request = new UserActivateRequest();
        request.setEmail(Base64.getEncoder().encodeToString("foo@bar.ch".getBytes()));
        request.setActivationCode(Base64.getEncoder().encodeToString("DEF".getBytes()));

        var exception = Assertions.assertThrows(InvalidDataException.class, () -> service.activate(request));
        assert ((exception.getMessages().get("_").contains("Wrong activation code")));
    }
}
