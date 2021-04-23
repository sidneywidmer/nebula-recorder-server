package recorder.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.javalin.Javalin;

import javax.inject.Singleton;

public class JavalinConfig extends AbstractModule {

    @Provides
    @Singleton
    public Javalin provideJavalin() {
        return Javalin.create();
    }
}
