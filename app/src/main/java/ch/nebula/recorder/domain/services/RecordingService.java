package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.domain.models.Recording;
import ch.nebula.recorder.domain.models.User;
import ch.nebula.recorder.domain.requests.RecordingUploadRequest;

import java.util.Map;

public class RecordingService {

    /**
     * After a user finished a recording on the client it can be uploaded to the server.
     */
    public void upload(User user, RecordingUploadRequest recordingUploadRequest) throws InvalidDataException {
        if (user == null) {
            throw new InvalidDataException(Map.of("-", "Illegal State"));
        }

        String name = user.getId() + "-" + recordingUploadRequest.getName();
        var recording = new Recording(name, recordingUploadRequest.getType(), user);

        String description = recordingUploadRequest.getDescription();
        if (description != null && !"".equals(description)) {
            recording.setDescription(description);
        }

        recording.save();
    }
}
