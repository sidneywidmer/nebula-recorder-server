package ch.nebula.recorder.domain.requests;

import ch.nebula.recorder.core.RecordingType;
import io.ebean.annotation.NotNull;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RecordingUploadRequest {
    @Size(max = 30)
    @NotNull
    String name;

    @NotNull
    RecordingType type;

    @NotNull
    @Lob
    byte[] recording;

    @Email
    @NotBlank
    String email;

    public RecordingType getType() {
        return type;
    }

    public void setType(RecordingType type) {
        this.type = type;
    }

    public byte[] getRecording() {
        return recording;
    }

    public void setRecording(byte[] recording) {
        this.recording = recording;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
