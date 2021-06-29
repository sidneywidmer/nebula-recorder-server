package ch.nebula.recorder;

import ch.nebula.recorder.core.Generator;
import ch.nebula.recorder.core.Hasher;
import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.domain.models.User;
import ch.nebula.recorder.domain.models.query.QUser;
import ch.nebula.recorder.domain.requests.UserActivateRequest;
import ch.nebula.recorder.domain.requests.UserSignupRequest;
import ch.nebula.recorder.domain.services.UserService;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends BaseTest {
    private final UserService service;

    public UserServiceTest() {
        service = new UserService(new Hasher(), new Generator());
    }

    @Test
    public void givenNewUser_testCreate() throws ApiException {
        var request = new UserSignupRequest();
        request.setEmail("foo@bar.ch");
        request.setPassword("hunter123");

        var user = service.create(request);

        assertEquals(request.getEmail(), user.getEmail());
    }

    @Test
    public void givenExistingUser_testCreateThrows() throws ApiException {
        var request = new UserSignupRequest();
        request.setEmail("foo@bar.ch");
        request.setPassword("hunter123");

        service.create(request);

        var exception = assertThrows(InvalidDataException.class, () -> service.create(request));
        assertTrue(exception.getMessages().get("_").contains("User already exists"));
    }

    @Test
    public void givenActivationRequest_testHappyPath() throws ApiException {
        var newUser = new User("foo@bar.ch", "hunter123");
        newUser.setActivationCode("ABC");
        newUser.save();

        var request = new UserActivateRequest();
        request.setEmail(Base64.getEncoder().encodeToString("foo@bar.ch".getBytes()));
        request.setActivationCode(Base64.getEncoder().encodeToString("ABC".getBytes()));

        service.activate(request);
        var user = new QUser().email.equalTo("foo@bar.ch").findOne();
        assertNotNull(user.getWhenActivated());
    }
}
