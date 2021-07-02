package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.BaseTest;
import ch.nebula.recorder.core.TemplateEngine;
import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.domain.models.User;
import com.typesafe.config.ConfigFactory;
import net.sargue.mailgun.Configuration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MailServiceTest extends BaseTest {
    private final MailService service;

    public MailServiceTest() {
        var config = ConfigFactory.load("app");
        var mailConfig = new Configuration().domain(config.getString("mail.domain"))
                .apiKey(config.getString("mail.api-key"))
                .apiUrl("https://api.eu.mailgun.net/v3")
                .from(config.getString("mail.address"));

        service = new MailService(mailConfig, new TemplateEngine(), config);
    }

    @Test
    public void givenNewUser_sendActivationMailHappyPath() throws ApiException {
        var newUser = new User("foo@bar.ch", "hunter123");
        newUser.setActivationCode("ABC");
        newUser.save();

        service.sendActivationMail(newUser);
    }

    @Test
    public void givenNewUser_sendActivationMailThrowsActivationMailNotSent() throws ApiException {
        var newUser = new User("foobar.ch", "hunter123");
        newUser.setActivationCode("ABC");
        newUser.save();

        var exception = assertThrows(InvalidDataException.class, () -> service.sendActivationMail(newUser));
        assertTrue(exception.getMessages().get("_").contains("Activation mail not sent"));
    }
}
