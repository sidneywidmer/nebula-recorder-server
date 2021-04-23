package recorder.web.controllers;

import io.javalin.http.Context;
import recorder.domain.models.User;

import javax.inject.Singleton;

import static io.javalin.plugin.rendering.template.TemplateUtil.model;


@Singleton
public class WelcomeController {
    public void index(Context ctx) {
        var user = new User("hello@sidney.dev");
        user.save();

        ctx.render("templates/base.peb", model("user", user));
    }
}
