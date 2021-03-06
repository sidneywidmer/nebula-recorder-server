package ch.nebula.recorder.domain.models;

import ch.nebula.recorder.core.RecordingType;
import io.ebean.annotation.NotNull;
import org.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "recordings")
public class Recording extends BaseModel {
    @Size(max = 30)
    @NotNull
    String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    RecordingType recordingType;

    @Size(max = 50)
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    User user;

    @NotNull
    UUID uuid;

    public Recording(String name, RecordingType recordingType, User user, UUID uuid) {
        this.name = name;
        this.recordingType = recordingType;
        this.user = user;
        this.uuid = uuid;
    }

    public String getUrl() {
        return "/recordings/" + getFileName();
    }

    public String getFileName() {
        return uuid.toString() + ".gif";
    }

    public String toJson() {
        var jsonObject = new JSONObject()
                .put("id", getUUID())
                .put("name", getName())
                .put("url", getUrl());

        return jsonObject.toString(1);
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

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }
}
