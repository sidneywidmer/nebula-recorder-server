package recorder.domain.requests;

import javax.validation.constraints.*;

public class UserSignupRequest {
    @Email
    @NotBlank
    String email;

    @Size(min = 8, max = 255)
    @NotBlank
    String password;

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
}
