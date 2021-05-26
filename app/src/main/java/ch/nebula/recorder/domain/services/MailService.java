package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.core.Generator;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.domain.models.User;
import com.google.inject.Inject;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.Response;

import java.util.Base64;
import java.util.Map;

public class MailService {
    private final Configuration mailGunConfig;

    @Inject
    public MailService(Configuration mailGunConfig, Generator generator) {
        this.mailGunConfig = mailGunConfig;
    }

    /**
     * After creating a new user it has an activation code. This code must be verified in the activation process
     * by clicking on a link which is sent to the users email.
     */
    public void sendActivationMail(User user) throws InvalidDataException {
        String queryString = generateQueryString(user.getEmail(), user.getActivationCode());

        //move url into app.conf with env variable
        String link = "https://nebula.sidney.dev/#/activate" + queryString;

        //Template engine in /resources activationMail.html
        //TemplateEngine.render.activationMail.html(using)
        // watch out https://www.mailgun.com/ or https://github.com/mailgun/transactional-email-templates/blob/master/templates/inlined/action.html or
        Response response = Mail.using(mailGunConfig)
                .body()
                .link(link)
                .mail()
                .to("oliverisler93@gmail.com") // TODO: replace string with user.getEmail()
                .subject("Activation code")
                .build()
                .send();

        if (!response.isOk()) {
            throw new InvalidDataException(Map.of("_", "Activation mail not sent"));
        }
    }

    private String generateQueryString(String email, String activationCode) {
        return String.format("?email=%s&activationCode=%s", encode(email), encode(activationCode));
    }

    private String encode(String s) {
        return Base64.getUrlEncoder().encodeToString(s.getBytes());
    }
}
