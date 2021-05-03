package ch.nebula.recorder.core;

import io.javalin.Javalin;

public interface Component {
    public void register(Javalin app);
}
