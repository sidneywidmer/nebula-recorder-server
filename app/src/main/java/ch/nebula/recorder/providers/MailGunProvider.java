package ch.nebula.recorder.providers;

import com.typesafe.config.Config;
import net.sargue.mailgun.Configuration;

import javax.inject.Inject;
import javax.inject.Provider;

public class MailGunProvider implements Provider<Configuration> {
    private final Config config;

    @Inject
    public MailGunProvider(Config config) {
        this.config = config;
    }

    /**
     * Create new MailGun Configuration to send mails.
     */
    @Override
    public Configuration get() {
        return new Configuration()
                .domain(config.getString("mail.domain"))
                .apiKey(config.getString("mail.api-key"))
                .from("Test account", config.getString("mail.address"));
    }
}
