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
import org.json.JSONArray;
import org.json.JSONObject;

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
    public void upload(User user, RecordingUploadRequest recordingUploadRequest) throws InvalidDataException {
        if (user == null) {
            throw new InvalidDataException(Map.of("-", "Illegal State"));
        }

        var name = user.getId() + "-" + recordingUploadRequest.getName();
        var recording = new QRecording().name.equalTo(name).findOne();
        if (recording != null) {
            throw new InvalidDataException(Map.of("-", "Recording already exists"));
        }

        var data = recordingUploadRequest.getRecording();
        write(name, data);

        var description = recordingUploadRequest.getDescription();
        var recordingType = recordingUploadRequest.getType();
        create(name, recordingType, description, user);
    }

    /**
     * If a user wants to see all his uploaded recordings this method generates a JSON of all available recordings by
     * validating if the passed user id exists.
     */
    public String getAll(User user) throws RecordingNotFoundException {
        List<Recording> recordings = new QRecording().user.equalTo(user).findList();

        var jsonArray = new JSONArray();
        for (Recording recording : recordings) {
            var jsonObject = new JSONObject();
            jsonObject.put("id", recording.getId());
            jsonObject.put("name", recording.getName());
            jsonObject.put("url", url(recording));

            jsonArray.put(jsonObject);
        }

        return jsonArray.toString(1);
    }

    /**
     * If a user wants so see a specific recording this method generates a JSON of the specific recording if it is
     * available.
     */
    public String getOne(long id) throws RecordingNotFoundException {
        var recording = new QRecording().id.equalTo(id).findOne();
        if (recording == null) {
            throw new RecordingNotFoundException(String.format("Recording with id: %d not found.", id));
        }

        var jsonObject = new JSONObject();
        jsonObject.put("id", recording.getId());
        jsonObject.put("name", recording.getName());
        jsonObject.put("url", url(recording));

        return jsonObject.toString(1);
    }

    private void write(String name, UploadedFile data) {
        var path = Paths.get(config.getString("storage.recordings-path"), name);
        FileUtil.streamToFile(data.getContent(), path.toString());
    }

    private void create(String name, RecordingType recordingType, String description, User user) {
        var recording = new Recording(name, recordingType, user);

        if (description != null && !"".equals(description)) {
            recording.setDescription(description);
        }

        recording.save();
    }

    private String url(Recording recording) {
        return "/recordings/" + recording.getName();
    }
}
