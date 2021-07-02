package ch.nebula.recorder.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeneratorTest {
    private final Generator generator;

    public GeneratorTest() {
        this.generator = new Generator();
    }

    @Test
    public void generateActivationCodeTest() {
        String activationCode = generator.generateActivationCode();

        Assertions.assertEquals(10, activationCode.length());
    }
}
