package ch.nebula.recorder.web.controllers;

import io.javalin.http.Context;

import javax.inject.Singleton;

import static io.javalin.plugin.rendering.template.TemplateUtil.model;


@Singleton
public class WelcomeController {
    public void index(Context ctx) {
        ctx.render("base.peb");
    }
}
