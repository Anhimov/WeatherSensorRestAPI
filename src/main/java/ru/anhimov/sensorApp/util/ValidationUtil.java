package ru.anhimov.sensorApp.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ru.anhimov.sensorApp.exception.MeasurementNotValidException;
import ru.anhimov.sensorApp.exception.SensorNotValidException;

@Component
public class ValidationUtil {

    public void validateAndThrow(Validator validator,
                                 Object target,
                                 BindingResult bindingResult,
                                 RuntimeException exception) {

        validator.validate(target, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();

            bindingResult.getFieldErrors()
                    .forEach(error -> sb.append(error.getField())
                            .append(" - ")
                            .append(error.getDefaultMessage())
                            .append(";"));

            throw createExceptionWithMessage(exception, sb.toString());
        }
    }

    private RuntimeException createExceptionWithMessage(RuntimeException exception, String message) {
        if (exception instanceof SensorNotValidException) {
            return new SensorNotValidException(message);
        } else if (exception instanceof MeasurementNotValidException) {
            return new MeasurementNotValidException(message);
        }
        return exception;
    }
}
