package ch.nebula.recorder;

import ch.nebula.recorder.core.Generator;
import ch.nebula.recorder.core.Hasher;
import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.domain.models.query.QUser;
import ch.nebula.recorder.domain.requests.UserSignupRequest;
import ch.nebula.recorder.domain.services.UserService;
import org.junit.jupiter.api.Test;

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

        service.create(request);

        var user = new QUser().email.equalTo(request.getEmail()).findOne();
        assertEquals(request.getEmail(), user.getEmail());
    }

    @Test
    public void givenExistingUser_testCreateThrows() throws ApiException {
        var request = new UserSignupRequest();
        request.setEmail("foo@bar.ch");
        request.setPassword("hunter123");

        service.create(request);

        var exception = assertThrows(InvalidDataException.class, () -> service.create(request));
        var first = exception.getMessages();

        assertTrue(exception.getMessages().get("_").contains("User already exists"));
    }
}
