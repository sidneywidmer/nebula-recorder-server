package ch.nebula.recorder;

import ch.nebula.recorder.domain.models.User;
import ch.nebula.recorder.domain.models.query.QUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Simple dummy test just to ensure our h2 testing configuration works.
 */
public class DummyTest {

    @Test
    public void dummy() {
        var user = new User("foo@bar.ch", "hunter123");
        user.save();

        var fetch = new QUser().email.equalTo("foo@bar.ch").findOne();
        assertEquals("foo@bar.ch", fetch.getEmail());
    }
}
