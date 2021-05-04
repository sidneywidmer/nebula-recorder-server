package ch.nebula.recorder.domain.auth;

import ch.nebula.recorder.domain.services.AuthService;
import io.javalin.core.security.AccessManager;
import io.javalin.core.security.Role;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.Set;

import static ch.nebula.recorder.domain.auth.AuthRoles.ANYONE;


public class JWTAccessManager implements AccessManager {
    private final AuthService auth;
    private final static String JWT_ATTR = "jwt-token";


    @Inject
    public JWTAccessManager(AuthService auth) {
        this.auth = auth;
    }

    @Override
    public void manage(@NotNull Handler handler, @NotNull Context ctx, @NotNull Set<Role> permittedRoles)
            throws Exception {
        // No specific roles given, we assume the route is public
        if (permittedRoles.isEmpty() || permittedRoles.contains(ANYONE)) {
            handler.handle(ctx);
        } else {
            var token = this.auth.getJWTFromHeader(ctx.header("Authorization"));
            ctx.attribute(JWT_ATTR, token);
        }

        handler.handle(ctx);
    }

}
