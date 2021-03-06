package ch.nebula.recorder.domain.auth;

import ch.nebula.recorder.core.exceptions.PermissionDeniedException;
import ch.nebula.recorder.domain.models.query.QUser;
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


    @Inject
    public JWTAccessManager(AuthService auth) {
        this.auth = auth;
    }

    @Override
    public void manage(@NotNull Handler handler, @NotNull Context ctx, @NotNull Set<Role> permittedRoles)
            throws Exception {
        if (!permittedRoles.isEmpty() && !permittedRoles.contains(ANYONE)) {
            var token = this.auth.getJWTFromHeader(ctx.header("Authorization"));
            var user = new QUser().id.equalTo(token.getClaim("user").asInt()).findOne();
            if (user == null) {
                throw new PermissionDeniedException();
            }
            ctx.attribute("user", user);
        }
        handler.handle(ctx);
    }

}
