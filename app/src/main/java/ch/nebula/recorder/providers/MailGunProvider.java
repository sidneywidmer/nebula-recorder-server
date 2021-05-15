package ch.nebula.recorder.providers;

import net.sargue.mailgun.Configuration;

import javax.inject.Provider;

public class MailGunProvider implements Provider<Configuration> {
    /**
     * Create new MailGun Configuration.
     */
    @Override
    public Configuration get() {
        return new Configuration()
                .domain("***REMOVED***")
                .apiKey("***REMOVED***")
                .from("***REMOVED***");
    }
}
