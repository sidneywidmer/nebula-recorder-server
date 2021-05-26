package ch.nebula.recorder.core;


import org.mindrot.jbcrypt.BCrypt;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Hasher {
    /**
     * Create a salted bcrypt hash of given password string
     */
    public String make(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean check(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
