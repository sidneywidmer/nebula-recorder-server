package ch.nebula.recorder.providers;

import net.sargue.mailgun.Configuration;

import javax.inject.Provider;

public class MailGunProvider implements Provider<Configuration> {
    /**
     * Create new MailGun Configuration to send mails.
     */
    @Override
    public Configuration get() {
        return new Configuration()
                .domain("sandbox1791bc77d949423db055ad0edc7cb05c.mailgun.org")
                .apiKey("c02890d811029c24498c5eb9f38391c5-2a9a428a-ac8a9c43")
                .from("Test account", "postmaster@sandbox1791bc77d949423db055ad0edc7cb05c.mailgun.org");
    }
}
