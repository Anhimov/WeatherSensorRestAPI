package ru.anhimov.sensorApp.exception;

public class PersonNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return "Person not found";
    }
}
