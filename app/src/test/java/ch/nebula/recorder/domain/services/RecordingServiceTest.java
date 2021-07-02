package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.BaseTest;
import ch.nebula.recorder.core.Generator;
import ch.nebula.recorder.core.Hasher;
import ch.nebula.recorder.core.RecordingType;
import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.core.exceptions.RecordingNotFoundException;
import ch.nebula.recorder.domain.models.User;
import ch.nebula.recorder.domain.models.query.QUser;
import ch.nebula.recorder.domain.requests.LoginRequest;
import ch.nebula.recorder.domain.requests.RecordingUploadRequest;
import ch.nebula.recorder.domain.requests.UserActivateRequest;
import ch.nebula.recorder.domain.requests.UserSignupRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.javalin.http.UploadedFile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
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

    public User getUser() {
        var user = new User("foo@bar.ch", "super-secret-hash");
        user.setActivationCode("activateme");
        user.save();

        return user;
    }

    public UploadedFile getUpload() throws FileNotFoundException {
        var dummyFile = new File("src/test/resources/dummy.gif");
        return new UploadedFile(
                new FileInputStream(dummyFile),
                "GIF",
                0,
                "example.gif",
                ".gif",
                0
        );
    }

    @Test
    public void givenUploadRequest_happyPath() throws ApiException, IOException {
        var user = getUser();
        var uploadedFile = getUpload();

        var recordingUploadRequest = new RecordingUploadRequest();
        recordingUploadRequest.setRecording(uploadedFile);
        recordingUploadRequest.setName(uploadedFile.getFilename());
        recordingUploadRequest.setType(RecordingType.GIF);
        recordingUploadRequest.setDescription("this is a sample gif");

        recordingService.upload(user, recordingUploadRequest);
    }

    @Test
    public void givenUploadRequest_throwsFileAlreadyExists() throws ApiException, IOException {
        var user = getUser();
        var uploadedFile = getUpload();

        var recordingUploadRequest = new RecordingUploadRequest();
        recordingUploadRequest.setRecording(uploadedFile);
        recordingUploadRequest.setName(uploadedFile.getFilename());
        recordingUploadRequest.setType(RecordingType.GIF);
        recordingUploadRequest.setDescription("this is a sample gif");

        recordingService.upload(user, recordingUploadRequest);

        var exception = assertThrows(InvalidDataException.class, () -> recordingService.upload(user, recordingUploadRequest));
        assertTrue(exception.getMessages().get("_").contains("Recording already exists"));
    }

    @Test
    public void givenUploadRequest_getOneHappyPath() throws ApiException, FileNotFoundException {
        var user = getUser();
        var uploadedFile = getUpload();

        var recordingUploadRequest = new RecordingUploadRequest();
        recordingUploadRequest.setRecording(uploadedFile);
        recordingUploadRequest.setName(uploadedFile.getFilename());
        recordingUploadRequest.setType(RecordingType.GIF);
        recordingUploadRequest.setDescription("this is a sample gif");

        recordingService.upload(user, recordingUploadRequest);
        recordingService.getOne(user.getId());
    }

    @Test
    public void givenUploadRequest_getOneThrowsRecordingNotFoundException() throws ApiException {
        var exception = assertThrows(RecordingNotFoundException.class, () -> recordingService.getOne(99));
        assertTrue(exception.getMessage().contains("Recording with id: 99 not found."));
    }

    @Test
    public void givenUploadRequest_getAllHappyPath() throws ApiException, FileNotFoundException {
        var user = getUser();
        var uploadedFile = getUpload();

        var recordingUploadRequest = new RecordingUploadRequest();
        recordingUploadRequest.setRecording(uploadedFile);
        recordingUploadRequest.setName(uploadedFile.getFilename());
        recordingUploadRequest.setType(RecordingType.GIF);
        recordingUploadRequest.setDescription("this is a sample gif");


        recordingService.upload(user, recordingUploadRequest);
        recordingService.getAll(user);
    }
}
