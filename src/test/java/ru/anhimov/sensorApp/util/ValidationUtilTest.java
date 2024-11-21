package ru.anhimov.sensorApp.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import ru.anhimov.sensorApp.exception.MeasurementNotValidException;
import ru.anhimov.sensorApp.exception.SensorNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidationUtilTest {

    public static final String OBJECT_NAME_SENSOR = "Sensor";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_TYPE = "type";
    public static final String MESSAGE_MUST_NOT_BE_NULL = "must not be null";
    public static final String MESSAGE_MUST_BE_A_VALID_TYPE = "must be a valid type";
    public static final String OBJECT_NAME_MEASUREMENT = "Measurement";
    public static final String FIELD_VALUE = "value";
    public static final String MESSAGE_MUST_BE_POSITIVE = "must be positive";
    public static final String MESSAGE_VALIDATION_FAILED = "Validation failed";
    public static final String MESSAGE_UNKNOWN_ERROR = "Unknown error";
    private ValidationUtil validationUtil;

    @Mock
    private Validator validator;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validationUtil = new ValidationUtil();
    }

    @Test
    void validateAndThrowShouldNotThrowWhenNoErrors() {
        Object target = new Object();
        when(bindingResult.hasErrors()).thenReturn(false);

        assertDoesNotThrow(() ->
                validationUtil.validateAndThrow(validator, target, bindingResult, new SensorNotValidException("")));
        verify(validator, times(1)).validate(target, bindingResult);
    }

    @Test
    void validateAndThrowShouldThrowSensorNotValidExceptionWhenBindingResultHasErrors() {
        Object target = new Object();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new org.springframework.validation.FieldError(OBJECT_NAME_SENSOR, FIELD_NAME, MESSAGE_MUST_NOT_BE_NULL),
                new org.springframework.validation.FieldError(OBJECT_NAME_SENSOR, FIELD_TYPE, MESSAGE_MUST_BE_A_VALID_TYPE)
        ));

        SensorNotValidException exception = assertThrows(SensorNotValidException.class, () ->
                validationUtil.validateAndThrow(validator, target, bindingResult, new SensorNotValidException(MESSAGE_VALIDATION_FAILED))
        );

        assertTrue(exception.getMessage().contains(FIELD_NAME + " - " + MESSAGE_MUST_NOT_BE_NULL + ";"));
        assertTrue(exception.getMessage().contains(FIELD_TYPE + " - " + MESSAGE_MUST_BE_A_VALID_TYPE + ";"));
        verify(validator, times(1)).validate(target, bindingResult);
    }

    @Test
    void validateAndThrowShouldThrowMeasurementNotValidExceptionWhenBindingResultHasErrors() {
        Object target = new Object();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new org.springframework.validation.FieldError(OBJECT_NAME_MEASUREMENT, FIELD_VALUE, MESSAGE_MUST_BE_POSITIVE)
        ));

        MeasurementNotValidException exception = assertThrows(MeasurementNotValidException.class, () ->
                validationUtil.validateAndThrow(validator, target, bindingResult, new MeasurementNotValidException(MESSAGE_VALIDATION_FAILED))
        );

        assertTrue(exception.getMessage().contains(FIELD_VALUE + " - " + MESSAGE_MUST_BE_POSITIVE + ";"));
        verify(validator, times(1)).validate(target, bindingResult);
    }

    @Test
    void validateAndThrowShouldThrowGivenExceptionWhenExceptionTypeIsUnknown() {
        Object target = new Object();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new org.springframework.validation.FieldError(OBJECT_NAME_SENSOR, FIELD_NAME, MESSAGE_MUST_NOT_BE_NULL)
        ));

        RuntimeException unknownException = new RuntimeException(MESSAGE_UNKNOWN_ERROR);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                validationUtil.validateAndThrow(validator, target, bindingResult, unknownException)
        );

        assertEquals(MESSAGE_UNKNOWN_ERROR, exception.getMessage());
        verify(validator, times(1)).validate(target, bindingResult);
    }
}

