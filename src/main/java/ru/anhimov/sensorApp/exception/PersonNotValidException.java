package ru.anhimov.sensorApp.exception;

public class PersonNotValidException extends RuntimeException {
    public PersonNotValidException(String message) {
        super(message);
    }
}
