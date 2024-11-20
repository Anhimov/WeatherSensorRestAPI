package ru.anhimov.sensorApp.exception;

public class MeasurementNotValidException extends RuntimeException {
    public MeasurementNotValidException(String message) {
        super(message);
    }

    public MeasurementNotValidException() {
    }
}
