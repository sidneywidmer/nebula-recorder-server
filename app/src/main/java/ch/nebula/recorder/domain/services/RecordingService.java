package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.core.RecordingType;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.core.exceptions.RecordingNotFoundException;
import ch.nebula.recorder.domain.models.Recording;
import ch.nebula.recorder.domain.models.User;
import ch.nebula.recorder.domain.models.query.QRecording;
import ch.nebula.recorder.domain.requests.RecordingUploadRequest;
import com.typesafe.config.Config;
import io.javalin.core.util.FileUtil;
import io.javalin.http.UploadedFile;

import javax.inject.Inject;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RecordingService {
    private final Config config;


    @Inject
    public RecordingService(Config config) {
        this.config = config;
    }

    /**
     * After a user finished a recording on the client it can be uploaded to the server.
     * Uploaded means the file is written on a filesystem and a recording dbo is generated.
     */
    public Recording upload(User user, RecordingUploadRequest recordingUploadRequest) throws InvalidDataException {
        if (user == null) {
            throw new InvalidDataException(Map.of("_", "Illegal state"));
        }

        var uuid = UUID.randomUUID();
        var name = recordingUploadRequest.getName();

        var data = recordingUploadRequest.getRecording();
        write(uuid.toString() + ".gif", data);

        var description = recordingUploadRequest.getDescription();
        var recordingType = recordingUploadRequest.getType();

        return create(name, recordingType, description, user, uuid);
    }

    /**
     * Get all recordings of given user.
     */
    public List<Recording> getAll(User user) throws RecordingNotFoundException {
        return new QRecording().user.equalTo(user).findList();
    }

    /**
     * Get one specific recording by id.
     */
    public Recording getOne(UUID uuid) throws RecordingNotFoundException {
        var recording = new QRecording().uuid.equalTo(uuid).findOne();
        if (recording == null) {
            throw new RecordingNotFoundException(String.format("Recording with id: %s not found.", uuid.toString()));
        }

        return recording;
    }

    private void write(String name, UploadedFile data) {
        var path = Paths.get(config.getString("storage.recordings-path"), name);
        FileUtil.streamToFile(data.getContent(), path.toString());
    }

    private Recording create(String name, RecordingType recordingType, String description, User user, UUID uuid) {
        var recording = new Recording(name, recordingType, user, uuid);

        if (description != null && !"".equals(description)) {
            recording.setDescription(description);
        }

        recording.save();

        return recording;
    }

}
