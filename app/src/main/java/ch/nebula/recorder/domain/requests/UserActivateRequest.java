package ch.nebula.recorder.domain.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Base64;

public class UserActivateRequest {
    @Email
    @NotBlank
    String email;

    @Size(min = 10, max = 10)
    @NotBlank
    String activationCode;

    public String getEmail() {
        return email;
    }

    /**
     * The user activation mail base64 encodes the data to avoid any url encoding
     * problems so here we have to decode again - same thing applies for
     * the setActivationCode method.
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = new String(Base64.getDecoder().decode(email));;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = new String(Base64.getDecoder().decode(activationCode));
    }
}
