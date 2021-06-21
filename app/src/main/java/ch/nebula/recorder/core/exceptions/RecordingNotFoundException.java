package ch.nebula.recorder.core.exceptions;

public class RecordingNotFoundException extends ApiException {
    private final String message;

    public RecordingNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
