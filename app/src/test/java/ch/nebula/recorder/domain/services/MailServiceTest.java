package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.BaseTest;
import ch.nebula.recorder.core.TemplateEngine;
import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.domain.models.User;
import com.typesafe.config.Config;
import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MailServiceTest extends BaseTest {
    private final MailService service;

    public MailServiceTest() {
        var mailgunConfig = mock(Configuration.class);
        var config = mock(Config.class);

        service = new MailService(mailgunConfig, new TemplateEngine(), config);
    }

    public void givenNewUser_sendActivationMailHappyPath() throws ApiException {
        var newUser = new User("foo@bar.ch", "hunter123");
        newUser.setActivationCode("ABC");
        newUser.save();

        var mail = mock(Mail.class);
        when(mail.send()).thenReturn(null);

        service.sendActivationMail(newUser);
    }

    public void givenNewUser_sendActivationMailThrowsActivationMailNotSent() throws ApiException {
        var newUser = new User("foobar.ch", "hunter123");
        newUser.setActivationCode("ABC");
        newUser.save();

        var exception = assertThrows(InvalidDataException.class, () -> service.sendActivationMail(newUser));
        assertTrue(exception.getMessages().get("_").contains("Activation mail not sent"));
    }
}
