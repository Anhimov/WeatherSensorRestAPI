package ru.anhimov.sensorApp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.anhimov.sensorApp.model.Measurement;
import ru.anhimov.sensorApp.model.Sensor;
import ru.anhimov.sensorApp.repository.MeasurementsRepository;
import ru.anhimov.sensorApp.repository.SensorRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MeasurementsServiceTest {
    public static final String TEST_SENSOR_NAME = "TestSensor";
    public static final String NON_EXISTENT_SENSOR = "NonExistentSensor";

    @Mock
    private MeasurementsRepository measurementsRepository;

    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private MeasurementsService measurementsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllShouldReturnListOfMeasurements() {
        List<Measurement> mockMeasurements = List.of(new Measurement(), new Measurement());
        when(measurementsRepository.findAll()).thenReturn(mockMeasurements);

        List<Measurement> result = measurementsService.findAll();

        assertEquals(2, result.size());
        verify(measurementsRepository, times(1)).findAll();
    }

    @Test
    void saveShouldEnrichAndSaveMeasurement() {
        Measurement measurement = new Measurement();
        Sensor sensor = new Sensor();
        sensor.setName(TEST_SENSOR_NAME);
        measurement.setSensor(sensor);

        Sensor foundSensor = new Sensor();
        foundSensor.setName(TEST_SENSOR_NAME);

        when(sensorRepository.findByName(TEST_SENSOR_NAME)).thenReturn(Optional.of(foundSensor));

        measurementsService.save(measurement);

        assertNotNull(measurement.getCreatedAt());
        assertEquals(TEST_SENSOR_NAME, measurement.getSensor().getName());
        verify(measurementsRepository, times(1)).save(measurement);
    }

    @Test
    void findMeasurementsBySensorNameShouldReturnMeasurements() {
        Sensor sensor = new Sensor();
        sensor.setName(TEST_SENSOR_NAME);

        List<Measurement> mockMeasurements = List.of(new Measurement(), new Measurement());
        when(sensorRepository.findByName(TEST_SENSOR_NAME)).thenReturn(Optional.of(sensor));
        when(measurementsRepository.findBySensorName(sensor)).thenReturn(mockMeasurements);

        List<Measurement> result = measurementsService.findMeasurementsBySensorName(TEST_SENSOR_NAME);

        assertEquals(2, result.size());
        verify(sensorRepository, times(1)).findByName(TEST_SENSOR_NAME);
        verify(measurementsRepository, times(1)).findBySensorName(sensor);
    }

    @Test
    void findMeasurementsBySensorNameShouldThrowExceptionIfSensorNotFound() {
        when(sensorRepository.findByName(NON_EXISTENT_SENSOR)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class,
                () -> measurementsService.findMeasurementsBySensorName(NON_EXISTENT_SENSOR));

        assertEquals("Sensor not found", exception.getMessage());
        verify(sensorRepository, times(1)).findByName(NON_EXISTENT_SENSOR);
    }
}
