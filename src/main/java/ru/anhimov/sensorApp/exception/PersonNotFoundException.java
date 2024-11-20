package ru.anhimov.sensorApp.exception;

import java.io.Serial;

public class PersonNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return "Person not found";
    }
}
