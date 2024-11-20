package ru.anhimov.sensorApp.util;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import ru.anhimov.sensorApp.model.Measurement;
import ru.anhimov.sensorApp.model.Sensor;
import ru.anhimov.sensorApp.service.SensorService;

import java.util.Objects;
import java.util.Optional;

public class MeasurementsValidatorTest {

    @InjectMocks
    private MeasurementsValidator measurementsValidator;

    @Mock
    private SensorService sensorService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateSensorIsNull() {
        Measurement measurement = new Measurement();
        Errors errors = new BeanPropertyBindingResult(measurement, "measurement");

        measurementsValidator.validate(measurement, errors);

        assertTrue(errors.hasErrors());
        assertEquals("Sensor must be provided", Objects.requireNonNull(errors.getFieldError("sensor"))
                .getDefaultMessage());
    }

    @Test
    public void testValidateSensorDoesNotExist() {
        Measurement measurement = new Measurement();
        Sensor sensor = new Sensor();
        sensor.setName("NonExistingSensor");
        measurement.setSensor(sensor);

        when(sensorService.findByName("NonExistingSensor")).thenReturn(Optional.empty());

        Errors errors = new BeanPropertyBindingResult(measurement, "measurement");
        measurementsValidator.validate(measurement, errors);

        assertTrue(errors.hasErrors());
        assertEquals("Sensor does not exist",
                Objects.requireNonNull(errors.getFieldError("sensor")).getDefaultMessage());
    }

    @Test
    public void testValidateSensorExists() {
        Measurement measurement = new Measurement();
        Sensor sensor = new Sensor();
        sensor.setName("ExistingSensor");
        measurement.setSensor(sensor);

        when(sensorService.findByName("ExistingSensor")).thenReturn(Optional.of(sensor));

        Errors errors = new BeanPropertyBindingResult(measurement, "measurement");
        measurementsValidator.validate(measurement, errors);

        assertFalse(errors.hasErrors());
    }
}

