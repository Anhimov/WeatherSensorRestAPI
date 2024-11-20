package ru.anhimov.sensorApp.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ru.anhimov.sensorApp.model.Sensor;
import ru.anhimov.sensorApp.service.SensorService;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SensorExistenceValidatorTest {
    private SensorExistenceValidator existenceValidator;
    private SensorService sensorService;

    @BeforeEach
    void setUp() {
        sensorService = Mockito.mock(SensorService.class);
        existenceValidator = new SensorExistenceValidator(sensorService);
    }

    @Test
    void validate_whenNameDoesNotExist_shouldRejectValue() {
        Sensor sensor = new Sensor();
        sensor.setName("NonExistentSensor");

        Errors errors = new BeanPropertyBindingResult(sensor, "sensor");

        when(sensorService.findByName("NonExistentSensor"))
                .thenReturn(Optional.empty());

        existenceValidator.validate(sensor, errors);

        verify(sensorService).findByName("NonExistentSensor");
        assert errors.hasFieldErrors("name");
        assert errors.getFieldError("name").getCode().equals("nameNotFound");
    }

    @Test
    void validate_whenNameExists_shouldNotRejectValue() {
        Sensor sensor = new Sensor();
        sensor.setName("ExistingSensor");

        Errors errors = new BeanPropertyBindingResult(sensor, "sensor");

        when(sensorService.findByName("ExistingSensor"))
                .thenReturn(Optional.of(sensor));

        existenceValidator.validate(sensor, errors);

        verify(sensorService).findByName("ExistingSensor");
        assert !errors.hasFieldErrors("name");
    }
}
