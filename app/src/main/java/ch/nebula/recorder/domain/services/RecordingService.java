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
            throw new InvalidDataException(Map.of("_", "Illegal State"));
        }

        var name = user.getId() + "-" + recordingUploadRequest.getName();
        var recording = new QRecording().name.equalTo(name).findOne();
        if (recording != null) {
            throw new InvalidDataException(Map.of("_", "Recording already exists"));
        }

        var data = recordingUploadRequest.getRecording();
        write(name, data);

        var description = recordingUploadRequest.getDescription();
        var recordingType = recordingUploadRequest.getType();

        return create(name, recordingType, description, user);
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
    public Recording getOne(long id) throws RecordingNotFoundException {
        var recording = new QRecording().id.equalTo(id).findOne();
        if (recording == null) {
            throw new RecordingNotFoundException(String.format("Recording with id: %d not found.", id));
        }

        return recording;
    }

    private void write(String name, UploadedFile data) {
        var path = Paths.get(config.getString("storage.recordings-path"), name);
        FileUtil.streamToFile(data.getContent(), path.toString());
    }

    private Recording create(String name, RecordingType recordingType, String description, User user) {
        var recording = new Recording(name, recordingType, user);

        if (description != null && !"".equals(description)) {
            recording.setDescription(description);
        }

        recording.save();

        return recording;
    }

}
