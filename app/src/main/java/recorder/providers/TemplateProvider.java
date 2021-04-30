package recorder.providers;


import com.google.inject.Provider;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;

public class TemplateProvider implements Provider<PebbleEngine> {
    /**
     * Set our default template folder to resources/templates to not pollute the * root resource folder with templates.
     * Like this we don't need the `template` prefix in every ctx.render and can just use it like so:
     * > ctx.render("base");
     */
    @Override
    public PebbleEngine get() {
        var loader = new ClasspathLoader();
        loader.setPrefix("templates");

        return new PebbleEngine.Builder()
                .loader(loader)
                .strictVariables(false)
                .build();
    }
}
