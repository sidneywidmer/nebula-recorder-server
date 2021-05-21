package ch.nebula.recorder.core;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Hasher {
    /**
     * Create a salted bcrypt hash of given password string
     */
    public String make(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public boolean check(String password, String hash) {
        var result = BCrypt.verifyer().verify(password.toCharArray(), hash);

        return result.verified;
    }
}
