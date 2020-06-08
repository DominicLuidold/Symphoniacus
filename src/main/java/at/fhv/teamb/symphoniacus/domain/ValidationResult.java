package at.fhv.teamb.symphoniacus.domain;

public class ValidationResult {

    private String message;
    private boolean isValid;

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
}
