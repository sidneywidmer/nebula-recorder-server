package ch.nebula.recorder.domain.services;

import com.google.inject.Inject;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;

public class MailService {
    private final Configuration configuration;

    @Inject
    public MailService(Configuration configuration) {
        this.configuration = configuration;
    }

    public void send() {
        Mail.using(configuration)
                .to("oliverisler93@gmail.com")
                .subject("Activation code")
                .text("A-123456789")
                .build()
                .send();
    }
}
