package ru.anhimov.sensorApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SensorNotValidException.class)
    public ResponseEntity<SensorErrorResponse> handleException(SensorNotValidException exception) {
        SensorErrorResponse response = new SensorErrorResponse();
        response.setMessage(exception.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MeasurementNotValidException.class)
    public ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotValidException exception) {
        MeasurementErrorResponse response = new MeasurementErrorResponse();
        response.setMessage(exception.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<PersonErrorResponse> handlePersonNotFoundException(PersonNotFoundException exception) {
        PersonErrorResponse response = new PersonErrorResponse();
        response.setMessage(exception.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersonNotValidException.class)
    public ResponseEntity<PersonErrorResponse> handlePersonNotValidException(PersonNotValidException exception) {
        PersonErrorResponse response = new PersonErrorResponse();
        response.setMessage(exception.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

