package recorder.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.github.cdimascio.dotenv.Dotenv;

import javax.inject.Singleton;

public class EnvConfig extends AbstractModule {
    @Provides
    @Singleton
    static Dotenv provideDotEnv() {
        return Dotenv.load();
    }
}
