package recorder.core;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class Hasher {
    /**
     * Create a salted PBKDF hash of given password string. The resulting hash is
     * returned as base64 encoded string.
     */
    public String make(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        var random = new SecureRandom();
        var salt = new byte[16];
        random.nextBytes(salt);

        var spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        var factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        var hash = factory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(hash);
    }

    public boolean check(String password, String hash) {
        return true;
    }
}
