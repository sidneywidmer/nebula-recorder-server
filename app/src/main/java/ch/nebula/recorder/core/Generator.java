package ch.nebula.recorder.core;

import java.security.SecureRandom;
import java.util.Random;

public class Generator {
    public static final int MAX_LENGTH = 10;
    public static final String ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789";
    private final Random random;

    public Generator() {
        this.random = new SecureRandom();
    }

    /**
     * Creates a random activation code as a String which is needed in the users activation process.
     */
    public String generateActivationCode() {
        StringBuilder stringBuilder = new StringBuilder(MAX_LENGTH);

        for (int i = 0; i < MAX_LENGTH; i++) {
            stringBuilder.append(ALLOWED_CHARS.charAt(random.nextInt(ALLOWED_CHARS.length())));
        }

        return stringBuilder.toString();
    }
}
