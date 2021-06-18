package ch.nebula.recorder.domain.requests;

import io.ebean.annotation.NotNull;

public class RecordingGetOneRequest {
    @NotNull
    long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
