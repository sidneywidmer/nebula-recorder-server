package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.core.Generator;
import ch.nebula.recorder.domain.models.User;
import com.google.inject.Inject;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.Response;

import java.util.Base64;

public class MailService {

    private final Configuration configuration;
    private final Generator generator;

    @Inject
    public MailService(Configuration configuration, Generator generator) {
        this.configuration = configuration;
        this.generator = generator;
    }

    /**
     * Try to send an activation mail to the new user
     */
    public Response sendActivationMail(User user, String activationCode) {
        String queryString = String.format("?email=%s&activationCode=%s", user.getEmail(), activationCode);
        String link = "https://nebula.sidney.dev/#/activate" + encode(queryString); // TODO: ask sidney, encode only values or whole query string?

        return Mail.using(configuration)
                .body()
                .link(link, "click here to activate")
                .mail()
                .to("oliverisler93@gmail.com") // TODO: replace string with user.getEmail()
                .subject("Activation code")
                .build()
                .send();
    }

    /**
     * Generates an activation code for the new user
     */
    public String generateActivationCode() {
        return generator.generateActivationCode();
    }

    private String encode(String s) {
        return Base64.getUrlEncoder().encodeToString(s.getBytes());
    }
}
