package ch.nebula.recorder.domain.requests;

import ch.nebula.recorder.core.RecordingType;
import io.ebean.annotation.NotNull;

import javax.validation.constraints.Size;

public class RecordingUploadRequest {
    @Size(max = 30)
    @NotNull
    String name;

    @NotNull
    RecordingType type;

    @Size(max = 50)
    String description;

    @NotNull
    String recording;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecordingType getType() {
        return type;
    }

    public void setType(RecordingType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecording() {
        return recording;
    }

    public void setRecording(String recording) {
        this.recording = recording;
    }
}
