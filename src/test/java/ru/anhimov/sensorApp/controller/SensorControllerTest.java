package ru.anhimov.sensorApp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.anhimov.sensorApp.service.SensorService;
import ru.anhimov.sensorApp.util.SensorValidator;
import ru.anhimov.sensorApp.util.ValidationUtil;

import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(SensorController.class)
public class SensorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorService sensorService;

    @MockBean
    private SensorValidator sensorValidator;

    @MockBean
    private ValidationUtil validationUtil;

    @Test
    public void testIndex() throws Exception {
        when(sensorService.findAll()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/sensors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.sensors").isArray());

        verify(sensorService, times(1)).findAll();
    }

    @Test
    public void testRegisterSensor() throws Exception {
        String sensorJson = "{\"name\":\"Sensor1\"}";

        doNothing().when(validationUtil).validateAndThrow(any(), any(), any(), any());

        mockMvc.perform(MockMvcRequestBuilders.post("/sensors/register")
                        .content(sensorJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(sensorService, times(1)).register(any());
    }
}
