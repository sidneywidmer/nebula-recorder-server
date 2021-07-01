package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.BaseTest;
import ch.nebula.recorder.core.Generator;
import ch.nebula.recorder.core.Hasher;
import ch.nebula.recorder.core.RecordingType;
import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.domain.requests.LoginRequest;
import ch.nebula.recorder.domain.requests.RecordingUploadRequest;
import ch.nebula.recorder.domain.requests.UserActivateRequest;
import ch.nebula.recorder.domain.requests.UserSignupRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import java.util.Base64;

public class RecordingServiceTest extends BaseTest {
    private final RecordingService recordingService;
    private final UserService userService;
    private final AuthService authService;
    private final Config config = ConfigFactory.load("test");

    public RecordingServiceTest() {
        var verifier = JWT.require(Algorithm.HMAC256(config.getString("auth.jwt-secret"))).build();
        recordingService = new RecordingService(config);
        userService = new UserService(new Hasher(), new Generator());
        authService = new AuthService(verifier, userService, config);
    }

    @Test
    public void givenUploadRequest_HappyPath() throws ApiException {
        var signupRequest = new UserSignupRequest();
        signupRequest.setEmail("foo@bar.ch");
        signupRequest.setPassword("hunter123");
        var user = userService.create(signupRequest);

        var userActivateRequest = new UserActivateRequest();
        userActivateRequest.setEmail(Base64.getEncoder().encodeToString(user.getEmail().getBytes()));
        userActivateRequest.setActivationCode(Base64.getEncoder().encodeToString(user.getActivationCode().getBytes()));
        userService.activate(userActivateRequest);

        var loginRequest = new LoginRequest();
        loginRequest.setEmail("foo@bar.ch");
        loginRequest.setPassword("hunter123");
        authService.generateToken(loginRequest);

        var recordingUploadRequest = new RecordingUploadRequest();
        recordingUploadRequest.setName("example3.gif");
        recordingUploadRequest.setType(RecordingType.GIF);
        recordingUploadRequest.setDescription("this is a sample gif");
        recordingUploadRequest.setRecording(config.getString("data").toString());


        recordingService.upload(user, recordingUploadRequest);


    }
}
