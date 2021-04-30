package recorder.core.exceptions;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InvalidDataException extends ApiException {
    private final HashMap<String, String> messages = new HashMap<>();

    /**
     * Parse given violations and put them in an easy serializable map.
     */
    public InvalidDataException(Set<ConstraintViolation<Object>> violations) {
        violations.forEach(violation -> {
            this.messages.put(
                    violation.getPropertyPath().toString(),
                    violation.getMessage()
            );
        });
    }

    public InvalidDataException(Map<String, String> violations) {
        violations.forEach(this.messages::put);
    }

    public HashMap<String, String> getMessages() {
        return this.messages;
    }
}
