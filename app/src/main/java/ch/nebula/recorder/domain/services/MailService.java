package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.core.Generator;
import ch.nebula.recorder.core.TemplateEngine;
import ch.nebula.recorder.domain.models.User;
import com.google.inject.Inject;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.Response;

import java.util.Base64;

import static io.javalin.plugin.rendering.template.TemplateUtil.model;

public class MailService {

    private final Configuration configuration;  //dont name it config
    private final Generator generator;
    private final TemplateEngine engine;

    @Inject
    public MailService(Configuration configuration, Generator generator, TemplateEngine engine) {
        this.configuration = configuration;
        this.generator = generator;
        this.engine = engine;
    }

    /**
     * Try to send an activation mail to the new user. // Kommentar machen der Beschreibt WIESO und nicht was
     * ...After creating a new user it has an activation code
     */
    public Response sendActivationMail(User user, String activationCode) {
        //encode only email and activationCode and not the whole query string
        String queryString = String.format("?email=%s&activationCode=%s", user.getEmail(), activationCode);

        //move url into app.conf with env variable
        String link = "https://nebula.sidney.dev/#/activate" + encode(queryString); // TODO: ask sidney, encode only values or whole query string?

        var body = engine.render("mails/userActivation.peb", model("user", user));

        //Template engine in /resources activationMail.html
        //TemplateEngine.render.activationMail.html(using)
        // watch out https://www.mailgun.com/ or https://github.com/mailgun/transactional-email-templates/blob/master/templates/inlined/action.html or
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
     * Generates an activation code for the new user.
     */
    public String generateActivationCode() {
        return generator.generateActivationCode();
    }

    private String encode(String s) {
        return Base64.getUrlEncoder().encodeToString(s.getBytes());
    }
}
