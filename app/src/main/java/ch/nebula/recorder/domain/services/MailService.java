package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.core.TemplateEngine;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.domain.models.User;
import com.google.inject.Inject;
import com.typesafe.config.Config;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.Response;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class MailService {

    private final Configuration mailGunConfig;
    private final TemplateEngine engine;
    private final Config config;

    @Inject
    public MailService(Configuration mailGunConfig, TemplateEngine engine, Config config) {
        this.mailGunConfig = mailGunConfig;
        this.engine = engine;
        this.config = config;
    }

    /**
     * After creating a new user it has an activation code. This code must be verified in the activation process
     * by clicking on a link which is sent to the users email.
     */
    public void sendActivationMail(User user) throws InvalidDataException {
        String queryString = generateQueryString(user.getEmail(), user.getActivationCode());
        String link = config.getString("mail.url") + queryString;

//        var body = engine.render("mails/userActivation.peb", model("user", user, "link", link));

        Map<String, Object> context = new HashMap<>();
        context.put("email", user.getEmail());
        context.put("url", link);
        var body = engine.render("mails/userActivation.peb", context);

        Response response = Mail.using(mailGunConfig)
                .to(user.getEmail())
                .subject("Activation code")
                .content(body)
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
