package ch.nebula.recorder.domain.models;

import ch.nebula.recorder.core.RecordingType;
import io.ebean.annotation.NotNull;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "recordings")
public class Recording extends BaseModel {
    @Size(max = 30)
    @NotNull
    String name;

    @NotNull
    RecordingType recordingType;

    @Size(max = 50)
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    User user;

    public Recording(String name, RecordingType recordingType, User user) {
        this.name = name;
        this.recordingType = recordingType;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
