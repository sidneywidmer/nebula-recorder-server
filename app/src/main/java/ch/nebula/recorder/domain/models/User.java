package ch.nebula.recorder.domain.models;


import io.ebean.annotation.NotNull;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Table(name = "users")
public class User extends BaseModel {
    @NotNull
    String email;

    @NotNull
    String password;

    Instant whenActivated;

    @Size(min = 10, max = 10)
    String activationCode;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Instant getWhenActivated() {
        return whenActivated;
    }

    public void setWhenActivated(Instant whenActivated) {
        this.whenActivated = whenActivated;
    }
}
