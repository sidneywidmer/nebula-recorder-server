package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.core.Generator;
import ch.nebula.recorder.domain.models.User;
import com.google.inject.Inject;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;

public class MailService {

    private final Configuration configuration;
    private final Generator generator;

    @Inject
    public MailService(Configuration configuration, Generator generator) {
        this.configuration = configuration;
        this.generator = generator;
    }

    /**
     * Try to send an activation mail to the new user by also creating an activation code.
     */
    public void sendActivationMail(User user) {
        Mail.using(configuration)
                .to("oliverisler93@gmail.com") // TODO: replace hard coded email with user.getEmail()
                .subject("Activation code")
                .text("Activation Code: " + generator.generateActivationCode())
                .build()
                .send();
    }
}
