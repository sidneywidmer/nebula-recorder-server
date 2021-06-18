package ch.nebula.recorder.domain.requests;

import io.ebean.annotation.NotNull;

import javax.validation.constraints.Size;

public class RecordingGetOneRequest {
    @Size(max = 30)
    @NotNull
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
