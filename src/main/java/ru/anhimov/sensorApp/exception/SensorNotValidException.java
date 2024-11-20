package ru.anhimov.sensorApp.exception;

public class SensorNotValidException extends RuntimeException {
    public SensorNotValidException(String message) {
        super(message);
    }

    public SensorNotValidException() {
    }
}
