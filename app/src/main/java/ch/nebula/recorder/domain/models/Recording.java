package ch.nebula.recorder.domain.models;

import ch.nebula.recorder.core.RecordingType;
import io.ebean.annotation.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "recordings")
public class Recording extends BaseModel {
    @Size(max = 30)
    @NotNull
    String name;

    @NotNull
    RecordingType recordingType;

    //TODO oli: ask sidney, if tag should be an own class
    @Size(max = 30)
    String tag;

    @Size(max = 50)
    String description;

    //TODO: ask sidney, what about limiting the size of a recording in bytes
    @Lob
    byte[] recording;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    User user;

    public Recording(String name, RecordingType recordingType, byte[] recording, User user) {
        this.name = name;
        this.recordingType = recordingType;
        this.recording = recording;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecordingType getRecordingType() {
        return recordingType;
    }

    public void setRecordingType(RecordingType recordingType) {
        this.recordingType = recordingType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
