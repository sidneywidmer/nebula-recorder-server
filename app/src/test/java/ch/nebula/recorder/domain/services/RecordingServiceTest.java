package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.BaseTest;
import ch.nebula.recorder.core.Generator;
import ch.nebula.recorder.core.Hasher;
import ch.nebula.recorder.core.RecordingType;
import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.core.exceptions.RecordingNotFoundException;
import ch.nebula.recorder.domain.requests.LoginRequest;
import ch.nebula.recorder.domain.requests.RecordingUploadRequest;
import ch.nebula.recorder.domain.requests.UserActivateRequest;
import ch.nebula.recorder.domain.requests.UserSignupRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.javalin.http.UploadedFile;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecordingServiceTest extends BaseTest {
    private final RecordingService recordingService;
    private final UserService userService;
    private final AuthService authService;
    private final Config config = ConfigFactory.load("app");

    public RecordingServiceTest() {
        var verifier = JWT.require(Algorithm.HMAC256(config.getString("auth.jwt-secret"))).build();
        recordingService = new RecordingService(config);
        userService = new UserService(new Hasher(), new Generator());
        authService = new AuthService(verifier, userService, config);
    }

    @Test
    public void givenUploadRequest_HappyPath() throws ApiException, IOException {
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
        var path = Paths.get(config.getString("storage.recordings-path"), "example.gif");
        InputStream inputStream = new FileInputStream(path.toFile());
        UploadedFile uploadedFile = new UploadedFile(inputStream, "GIF", 0, "example.gif", ".gif", 0);
        recordingUploadRequest.setRecording(uploadedFile);
        recordingUploadRequest.setName(uploadedFile.getFilename());
        recordingUploadRequest.setType(RecordingType.GIF);
        recordingUploadRequest.setDescription("this is a sample gif");

        recordingService.upload(user, recordingUploadRequest);
    }

    @Test
    public void givenUploadRequest_ThrowsFileAlreadyExists() throws ApiException, IOException {
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
        var path = Paths.get(config.getString("storage.recordings-path"), "example.gif");
        InputStream inputStream = new FileInputStream(path.toFile());
        UploadedFile uploadedFile = new UploadedFile(inputStream, "GIF", 0, "example.gif", ".gif", 0);
        recordingUploadRequest.setRecording(uploadedFile);
        recordingUploadRequest.setName(uploadedFile.getFilename());
        recordingUploadRequest.setType(RecordingType.GIF);
        recordingUploadRequest.setDescription("this is a sample gif");
        recordingService.upload(user, recordingUploadRequest);

        var exception = assertThrows(InvalidDataException.class, () -> recordingService.upload(user, recordingUploadRequest));
        assertTrue(exception.getMessages().get("_").contains("Recording already exists"));
    }

    @Test
    public void givenUploadRequest_GetOneHappyPath() throws ApiException, FileNotFoundException {
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
        var path = Paths.get(config.getString("storage.recordings-path"), "example.gif");
        InputStream inputStream = new FileInputStream(path.toFile());
        UploadedFile uploadedFile = new UploadedFile(inputStream, "GIF", 0, "example.gif", ".gif", 0);
        recordingUploadRequest.setRecording(uploadedFile);
        recordingUploadRequest.setName(uploadedFile.getFilename());
        recordingUploadRequest.setType(RecordingType.GIF);
        recordingUploadRequest.setDescription("this is a sample gif");

        recordingService.upload(user, recordingUploadRequest);
        recordingService.getOne(1);
    }

    @Test
    public void givenUploadRequest_GetOneThrowsRecordingNotFoundException() throws ApiException {
        var exception = assertThrows(RecordingNotFoundException.class, () -> recordingService.getOne(99));
        assertTrue(exception.getMessage().contains("Recording with id: 99 not found."));
    }

    @Test
    public void givenUploadRequest_GetAllHappyPath() throws ApiException, FileNotFoundException {
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
        var path = Paths.get(config.getString("storage.recordings-path"), "example.gif");
        InputStream inputStream = new FileInputStream(path.toFile());
        UploadedFile uploadedFile = new UploadedFile(inputStream, "GIF", 0, "example.gif", ".gif", 0);
        recordingUploadRequest.setRecording(uploadedFile);
        recordingUploadRequest.setName(uploadedFile.getFilename());
        recordingUploadRequest.setType(RecordingType.GIF);
        recordingUploadRequest.setDescription("this is a sample gif");

        recordingService.upload(user, recordingUploadRequest);
        recordingService.getAll(user);
    }
}
