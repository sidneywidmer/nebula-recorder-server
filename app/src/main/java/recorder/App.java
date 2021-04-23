package recorder;

import com.google.inject.Guice;
import recorder.config.Bootstrap;

public class App {
    public static void main(String[] args) {
        // Initialize DI
        var injector = Guice.createInjector(new Bootstrap());

        // 3..2..1.. LIFTOFF!
        injector.getInstance(Rocket.class).launch();
    }
}
