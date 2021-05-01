package ch.nebula.recorder.web;

import ch.nebula.recorder.core.exceptions.ApiException;
import ch.nebula.recorder.core.exceptions.InvalidDataException;
import ch.nebula.recorder.core.exceptions.SystemException;
import io.javalin.Javalin;

import static io.javalin.plugin.rendering.template.TemplateUtil.model;

public class ErrorHandler {
    /**
     * Register different kind of exceptions being thrown and how we should
     * process them.
     */
    public void register(Javalin app) {
        app.exception(InvalidDataException.class, (e, ctx) -> {
            ctx.status(422);
            ctx.json(model("message", "Invalid data given", "code", "data.invalid", "fields", e.getMessages()));
        });

        app.exception(ApiException.class, (e, ctx) -> {
            ctx.status(400);
            ctx.json(model("message", "Malformed or invalid json given", "code", "json.invalid"));
        });

        app.exception(SystemException.class, (e, ctx) -> {
            ctx.status(500);
            ctx.json(model("message", "An error occurred on the server", "code", "system.error"));
        });
    }
}
