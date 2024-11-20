package ru.anhimov.sensorApp.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.anhimov.sensorApp.dto.MeasurementDTO;
import ru.anhimov.sensorApp.dto.MeasurementsResponse;
import ru.anhimov.sensorApp.dto.SensorDTO;
import ru.anhimov.sensorApp.model.Measurement;
import ru.anhimov.sensorApp.model.Sensor;
import ru.anhimov.sensorApp.repository.MeasurementsRepository;
import ru.anhimov.sensorApp.repository.SensorRepository;
import ru.anhimov.sensorApp.service.MeasurementsService;
import ru.anhimov.sensorApp.service.SensorService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MeasurementsControllerIntegrationTest {
    public static final String TEST_SENSOR = "TestSensor";
    public static final BigDecimal VALUE_OF_FIRST_MEASUREMENT = BigDecimal.valueOf(25.5);
    public static final BigDecimal VALUE_OF_SECOND_MEASUREMENT = BigDecimal.valueOf(15.0);
    public static final int EXPECTED_SIZE_OF_MEASUREMENTS_LIST = 2;
    public static final String ENDPOINT_INDEX = "/measurements";
    public static final String ENDPOINT_REGISTER_MEASUREMENT = "/measurements/add";
    public static final String ENDPOINT_RAINY_DAYS_COUNT = "/measurements/rainyDaysCount";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private MeasurementsService measurementsService;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private MeasurementsRepository measurementsRepository;

    @BeforeEach
    void setup() {
        Sensor sensor = new Sensor();
        sensor.setName(TEST_SENSOR);
        sensorService.register(sensor);

        Measurement measurement1 = new Measurement();
        measurement1.setValue(VALUE_OF_FIRST_MEASUREMENT);
        measurement1.setRaining(true);
        measurement1.setSensor(sensor);

        Measurement measurement2 = new Measurement();
        measurement2.setValue(VALUE_OF_SECOND_MEASUREMENT);
        measurement2.setRaining(false);
        measurement2.setSensor(sensor);

        measurementsService.save(measurement1);
        measurementsService.save(measurement2);
    }

    @AfterEach
    void tearDown() {
        measurementsRepository.deleteAll();
        sensorRepository.deleteAll();
    }

    @Test
    public void testFindByName() {
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName(TEST_SENSOR);

        ResponseEntity<MeasurementsResponse> response = restTemplate.postForEntity(
                ENDPOINT_INDEX,
                sensorDTO,
                MeasurementsResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        MeasurementsResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMeasurements()).hasSize(EXPECTED_SIZE_OF_MEASUREMENTS_LIST);

        List<BigDecimal> actualValues = responseBody.getMeasurements()
                .stream()
                .map(MeasurementDTO::getValue)
                .collect(Collectors.toList());

        assertThat(actualValues)
                .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                .containsExactlyInAnyOrder(VALUE_OF_FIRST_MEASUREMENT, VALUE_OF_SECOND_MEASUREMENT);
    }

    @Test
    public void testIndex() {
        ResponseEntity<MeasurementsResponse> response = restTemplate.getForEntity(ENDPOINT_INDEX, MeasurementsResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        MeasurementsResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getMeasurements()).hasSize(EXPECTED_SIZE_OF_MEASUREMENTS_LIST);

        List<MeasurementDTO> actualMeasurements = responseBody.getMeasurements();

        assertThat(actualMeasurements)
                .extracting(MeasurementDTO::getValue)
                .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                .containsExactlyInAnyOrder(VALUE_OF_FIRST_MEASUREMENT, VALUE_OF_SECOND_MEASUREMENT);

        assertThat(actualMeasurements)
                .extracting(MeasurementDTO::isRaining)
                .containsExactlyInAnyOrder(true, false);

        assertThat(actualMeasurements)
                .extracting(dto -> dto.getSensor().getName())
                .containsOnly(TEST_SENSOR);
    }


    @Test
    public void testGetRainyDaysCount() {
        ResponseEntity<Long> response = restTemplate.getForEntity(ENDPOINT_RAINY_DAYS_COUNT, Long.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testRegisterMeasurement() {
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setValue(VALUE_OF_FIRST_MEASUREMENT);
        measurementDTO.setRaining(true);
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName(TEST_SENSOR);
        measurementDTO.setSensor(sensorDTO);

        ResponseEntity<Void> response = restTemplate.postForEntity(ENDPOINT_REGISTER_MEASUREMENT, measurementDTO, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}

