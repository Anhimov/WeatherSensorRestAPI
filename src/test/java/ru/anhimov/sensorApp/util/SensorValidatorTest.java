package ru.anhimov.sensorApp.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ru.anhimov.sensorApp.model.Sensor;
import ru.anhimov.sensorApp.service.SensorService;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SensorValidatorTest {
    private SensorService sensorService;
    private SensorValidator sensorValidator;

    @BeforeEach
    void setUp() {
        sensorService = Mockito.mock(SensorService.class);
        sensorValidator = new SensorValidator(sensorService);
    }

    @Test
    void validateWhenNameExistsShouldRejectValue() {
        Sensor sensor = new Sensor();
        sensor.setName("ExistingSensor");

        Errors errors = new BeanPropertyBindingResult(sensor, "sensor");

        when(sensorService.findByName("ExistingSensor"))
                .thenReturn(Optional.of(sensor));

        sensorValidator.validate(sensor, errors);

        verify(sensorService).findByName("ExistingSensor");
        assertTrue(errors.hasFieldErrors("name"));
        assertEquals("Name already exists",
                Objects.requireNonNull(errors.getFieldError("name")).getDefaultMessage());
    }

    @Test
    void validateWhenNameDoesNotExistShouldNotRejectValue() {
        Sensor sensor = new Sensor();
        sensor.setName("NewSensor");

        Errors errors = new BeanPropertyBindingResult(sensor, "sensor");

        when(sensorService.findByName("NewSensor"))
                .thenReturn(Optional.empty());

        sensorValidator.validate(sensor, errors);

        verify(sensorService).findByName("NewSensor");
        assert !errors.hasFieldErrors("name");
    }
}

