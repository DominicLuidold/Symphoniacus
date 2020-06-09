package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.application.ValidationResult;

public interface Validatable {

    ValidationResult<?> isValid();
}
