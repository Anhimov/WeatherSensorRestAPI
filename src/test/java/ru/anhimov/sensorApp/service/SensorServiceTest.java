package ru.anhimov.sensorApp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.anhimov.sensorApp.model.Sensor;
import ru.anhimov.sensorApp.repository.SensorRepository;

import static org.mockito.Mockito.*;

class SensorServiceTest {

    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private SensorService sensorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldEnrichAndSaveSensor() {
        Sensor sensor = new Sensor();
        sensor.setName("TestSensor");

        sensorService.register(sensor);

        verify(sensorRepository, times(1)).save(sensor);
    }
}
