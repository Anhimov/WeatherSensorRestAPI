package ru.anhimov.sensorApp.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.anhimov.sensorApp.dto.SensorDTO;
import ru.anhimov.sensorApp.dto.SensorsResponse;
import ru.anhimov.sensorApp.model.Sensor;
import ru.anhimov.sensorApp.repository.SensorRepository;
import ru.anhimov.sensorApp.service.SensorService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SensorControllerIntegrationTest {
    public static final String TEST_SENSOR_NAME = "TestSensor";
    public static final String ENDPOINT_INDEX = "/sensors";
    public static final String ENDPOINT_REGISTER = "/sensors/register";
    public static final String EMPTY_SENSOR_NAME = "";

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private SensorRepository sensorRepository;

    @AfterEach
    void tearDown() {
        sensorRepository.deleteAll();
    }

    @Test
    public void testIndex() {
        Sensor sensor = new Sensor();
        sensor.setName(TEST_SENSOR_NAME);
        sensorService.register(sensor);

        ResponseEntity<SensorsResponse> response = restTemplate.getForEntity(ENDPOINT_INDEX, SensorsResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        SensorsResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getSensors())
                .hasSize(1)
                .extracting(SensorDTO::getName)
                .containsExactly(TEST_SENSOR_NAME);
    }

    @Test
    public void testRegisterSensor() {
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName(TEST_SENSOR_NAME);

        ResponseEntity<Void> response = restTemplate.postForEntity(ENDPOINT_REGISTER, sensorDTO, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Sensor sensor = sensorService.findByName(TEST_SENSOR_NAME).orElse(null);
        assertThat(sensor).isNotNull();
        assertThat(sensor.getName()).isEqualTo(TEST_SENSOR_NAME);
    }

    @Test
    public void testRegisterInvalidSensor() {
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName(EMPTY_SENSOR_NAME);

        ResponseEntity<Void> response = restTemplate.postForEntity(ENDPOINT_REGISTER, sensorDTO, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(sensorService.findAll()).isEmpty();
    }
}
