package ch.nebula.recorder.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.inject.Provider;

public class JWTProvider implements Provider<JWTVerifier> {
    /**
     * Load our JWTVerifier with the secret key from the config file.
     */
    @Override
    public JWTVerifier get() {
        Algorithm algorithm = Algorithm.HMAC256("super-duper-secret");
        return JWT.require(algorithm).build();
    }
}
