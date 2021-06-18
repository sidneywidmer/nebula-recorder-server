package ch.nebula.recorder.domain.requests;

import io.ebean.annotation.NotNull;

public class RecordingGetAllRequest {
    @NotNull
    long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
