package ch.nebula.recorder.core;

import ch.nebula.recorder.core.exceptions.SystemException;
import com.mitchellbosecke.pebble.PebbleEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class TemplateEngine {
    private final PebbleEngine engine;

    public TemplateEngine() {
        engine = new PebbleEngine.Builder().build();
    }

    public String render(String template, Map<String, Object> context) {
        var writer = new StringWriter();
        var compiledTemplate = engine.getTemplate("templates/" + template);

        try {
            compiledTemplate.evaluate(writer, context);
        } catch (IOException e) {
            throw new SystemException(e);
        }

        return writer.toString();
    }
}
