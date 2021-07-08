package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.BaseTest;
import ch.nebula.recorder.core.RecordingType;
import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.core.exceptions.RecordingNotFoundException;
import ch.nebula.recorder.domain.models.Recording;
import ch.nebula.recorder.domain.models.User;
import ch.nebula.recorder.domain.requests.RecordingUploadRequest;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.javalin.http.UploadedFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordingServiceTest extends BaseTest {
    private final RecordingService recordingService;

    public RecordingServiceTest() {
        Config config = ConfigFactory.load("app");
        recordingService = new RecordingService(config);
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

        Recording recording = recordingService.upload(user, recordingUploadRequest);
        assert (recording != null);
        assert (recording.getName().contains(recordingUploadRequest.getName()));
        assert (recordingUploadRequest.getDescription().equals(recording.getDescription()));
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

        var recording = recordingService.upload(user, recordingUploadRequest);
        var fetchedRecording = recordingService.getOne(recording.getUUID());

        assertEquals(recording, fetchedRecording);
    }

    @Test
    public void givenUploadRequest_getOneThrowsRecordingNotFoundException() throws ApiException {
        // If this test ever fails because of a uuid collision i'll eat a broom
        var uuid = UUID.randomUUID();
        var exception = Assertions.assertThrows(RecordingNotFoundException.class, () -> recordingService.getOne(uuid));
        assert ((exception.getMessage().contains("Recording with id: " + uuid.toString() + " not found.")));
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

        var recording = recordingService.upload(user, recordingUploadRequest);
        var fetchedRecordings = recordingService.getAll(user);
        assertEquals(recording, fetchedRecordings.get(0));
    }
}
