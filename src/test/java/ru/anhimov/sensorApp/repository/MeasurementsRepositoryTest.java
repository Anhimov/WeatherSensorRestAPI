package ru.anhimov.sensorApp.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.anhimov.sensorApp.model.Measurement;
import ru.anhimov.sensorApp.model.Sensor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MeasurementsRepositoryTest {

    @Autowired
    private MeasurementsRepository measurementsRepository;

    @Autowired
    private SensorRepository sensorRepository;

    private Sensor testSensor;

    @BeforeEach
    void setUp() {
        testSensor = new Sensor();
        testSensor.setName("TestSensor");
        testSensor = sensorRepository.save(testSensor);

        Measurement measurement1 = new Measurement();
        measurement1.setValue(BigDecimal.valueOf(25.5));
        measurement1.setRaining(false);
        measurement1.setSensor(testSensor);
        measurement1.setCreatedAt(LocalDateTime.now());

        Measurement measurement2 = new Measurement();
        measurement2.setValue(BigDecimal.valueOf(28.3));
        measurement2.setRaining(true);
        measurement2.setSensor(testSensor);
        measurement2.setCreatedAt(LocalDateTime.now());

        measurementsRepository.saveAll(List.of(measurement1, measurement2));
    }

    @Test
    void findBySensorNameWhenSensorExistsReturnsMeasurements() {
        List<Measurement> measurements = measurementsRepository.findBySensorName(testSensor);

        assertThat(measurements).hasSize(2);
        assertThat(measurements).allMatch(m -> m.getSensor().equals(testSensor));
    }

    @Test
    void findBySensorNameWhenSensorDoesNotExistReturnsEmptyList() {
        Sensor newSensor = new Sensor();
        newSensor.setName("NonExistentSensor");
        newSensor = sensorRepository.save(newSensor);

        List<Measurement> measurements = measurementsRepository.findBySensorName(newSensor);

        assertThat(measurements).isEmpty();
    }
}

