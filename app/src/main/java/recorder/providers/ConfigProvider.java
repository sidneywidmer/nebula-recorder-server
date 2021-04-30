package recorder.providers;

import com.google.inject.Provider;
import com.typesafe.config.*;

public class ConfigProvider implements Provider<Config> {
    /**
     * Create new ConfigFactory and set the basename to app. The config is located in resources/app.conf
     * and gitignored - never push it to the repo.
     */
    @Override
    public Config get() {
        return ConfigFactory.load("app");
    }
}
