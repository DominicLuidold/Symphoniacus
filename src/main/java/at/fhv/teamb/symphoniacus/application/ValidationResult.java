package at.fhv.teamb.symphoniacus.application;

import java.util.Optional;

public class ValidationResult<T> {
    private final String message;
    private boolean isValid;
    private T payload;

    public ValidationResult(boolean isValid) {
        this("VALIDATED", isValid);
    }

    public ValidationResult(String message, boolean isValid) {
        this.message = message;
        this.isValid = isValid;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isValid() {
        return this.isValid;
    }

    void setValid(boolean isValid) { // Intentionally package-private
        this.isValid = isValid;
    }

    void setPayload(T payload) { // Intentionally package-private
        this.payload = payload;
    }

    public Optional<T> getPayload() {
        return Optional.ofNullable(payload);
    }
}
