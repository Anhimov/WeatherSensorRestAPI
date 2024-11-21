package ru.anhimov.sensorApp.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.anhimov.sensorApp.dto.MeasurementDTO;
import ru.anhimov.sensorApp.dto.SensorDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class GlobalExceptionHandlerIntegrationTest {
    public static final String ENDPOINT_SENSORS_REGISTER = "/sensors/register";
    public static final String ENDPOINT_MEASUREMENTS_ADD = "/measurements/add";

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Test
    void handleSensorNotValidException() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        SensorDTO invalidSensorDTO = new SensorDTO();

        mockMvc.perform(post(ENDPOINT_SENSORS_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSensorDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void handleMeasurementNotValidException() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        MeasurementDTO measurementDTO = new MeasurementDTO();

        mockMvc.perform(post(ENDPOINT_MEASUREMENTS_ADD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(measurementDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }
}






