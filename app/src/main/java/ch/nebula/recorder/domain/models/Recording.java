package ch.nebula.recorder.domain.models;

import io.ebean.annotation.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "recordings")
public class Recording extends BaseModel {
    @NotNull
    String name;

    @Lob
    byte[] recording;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    User user;


    public Recording(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
