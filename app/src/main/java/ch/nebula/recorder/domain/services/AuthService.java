package ch.nebula.recorder.domain.services;

import ch.nebula.recorder.core.exceptions.PermissionDeniedException;
import ch.nebula.recorder.core.exceptions.SystemException;
import ch.nebula.recorder.domain.requests.LoginRequest;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.typesafe.config.Config;

import javax.inject.Inject;
import java.util.Calendar;

public class AuthService {
    private final JWTVerifier verifier;
    private final UserService userService;
    private final Config config;

    @Inject
    public AuthService(JWTVerifier verifier, UserService userService, Config config) {
        this.verifier = verifier;
        this.userService = userService;
        this.config = config;
    }

    /**
     * Check if given credentials are valid and if yes, return a new signed jwt
     * token that is valid for one week.
     */
    public String generateToken(LoginRequest login) throws PermissionDeniedException, SystemException {
        var user = userService.byEmailAndPassword(login);
        if (user.isEmpty()) {
            throw new PermissionDeniedException();
        }

        var calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, 1);

        try {
            Algorithm algorithm = Algorithm.HMAC256(config.getString("auth.jwt-secret"));
            return JWT.create()
                    .withIssuer("nebula")
                    .withClaim("user", user.get().getId())
                    .withExpiresAt(calendar.getTime())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new SystemException(e);
        }
    }

    public DecodedJWT getJWTFromHeader(String header) throws PermissionDeniedException {
        var token = getTokenFromHeader(header);
        if (token == null) {
            throw new PermissionDeniedException();
        }

        return validateToken(token);
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
        if (header == null) {
            throw new PermissionDeniedException();
        }

        var split = header.split(" ");
        if (split.length != 2 || !split[0].equals("Bearer")) {
            throw new PermissionDeniedException();
        }

        return split[1];
    }
}
