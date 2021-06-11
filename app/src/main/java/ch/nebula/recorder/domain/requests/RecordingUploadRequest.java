package ch.nebula.recorder.domain.requests;

import ch.nebula.recorder.core.RecordingType;
import io.ebean.annotation.NotNull;

import javax.persistence.Lob;
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
    @Lob
    byte[] recording;

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

    public byte[] getRecording() {
        return recording;
    }

    public void setRecording(byte[] recording) {
        this.recording = recording;
    }
}
