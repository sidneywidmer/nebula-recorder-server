package ch.nebula.recorder.providers;

import net.sargue.mailgun.Configuration;

import javax.inject.Provider;

public class MailGunProvider implements Provider<Configuration> {
    /**
     * Create new MailGun Configuration to send mails.
     */
    @Override
    public Configuration get() {
        //move into app.conf
        return new Configuration()
                .domain("***REMOVED***")
                .apiKey("***REMOVED***")
                .from("Test account", "postmaster@***REMOVED***");
    }
}
