package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.domain.models.Recording;
import ch.nebula.recorder.domain.models.query.QUser;
import ch.nebula.recorder.domain.requests.RecordingUploadRequest;

import java.util.Map;

public class RecordingService {

    /**
     * After a user finished a recording on the client it can be uploaded to the server.
     *
     * @param recordingUploadRequest
     */
    public void upload(RecordingUploadRequest recordingUploadRequest) throws InvalidDataException {
        var user = new QUser().email.equalTo(recordingUploadRequest.getEmail()).findOne();
        if (user == null) {
            throw new InvalidDataException(Map.of("_", "User doesnt exist"));
        }

        var recording = new Recording(recordingUploadRequest.getName(), recordingUploadRequest.getType(), recordingUploadRequest.getRecording(), user);
        recording.save();
    }
}
