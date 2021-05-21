package ch.nebula.recorder.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.typesafe.config.Config;

public class JWTProvider implements Provider<JWTVerifier> {
    private final Config config;

    @Inject
    public JWTProvider(Config config) {
        this.config = config;
    }

    /**
     * Load our JWTVerifier with the secret key from the config file.
     */
    @Override
    public JWTVerifier get() {
        Algorithm algorithm = Algorithm.HMAC256(config.getString("auth.jwt-secret"));

        return JWT.require(algorithm).build();
    }
}
