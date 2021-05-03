package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.core.exceptions.PermissionDeniedException;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.inject.Inject;
import java.util.Optional;

public class AuthService {
    private final JWTVerifier verifier;

    @Inject
    public AuthService(JWTVerifier verifier) {
        this.verifier = verifier;
    }

    public void checkHeader(Optional<String> header) throws PermissionDeniedException {
        var token = getTokenFromHeader(header);
        if (token == null) {
            throw new PermissionDeniedException();
        }

        var validToken = validateToken(token.toString());

        throw new PermissionDeniedException();
    }

    public DecodedJWT validateToken(String token) throws PermissionDeniedException {
        try {
            return verifier.verify(token);
        } catch (JWTVerificationException ex) {
            throw new PermissionDeniedException();
        }
    }

    /**
     * The bearer header looks like this: Baerer XYZ-MY-TOKEN. We'll
     * try to split it and extract the token from that string.
     */
    private String getTokenFromHeader(String header) throws PermissionDeniedException {
        var split = header.split(" ");
        if (split.length != 2 || !split[0].equals("Bearer")) {
            throw new PermissionDeniedException();
        }

        return split[1];
    }
}
