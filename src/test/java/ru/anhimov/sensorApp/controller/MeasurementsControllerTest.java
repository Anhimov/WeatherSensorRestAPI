package ru.anhimov.sensorApp.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.anhimov.sensorApp.dto.MeasurementDTO;
import ru.anhimov.sensorApp.dto.SensorDTO;
import ru.anhimov.sensorApp.model.Measurement;
import ru.anhimov.sensorApp.model.Sensor;
import ru.anhimov.sensorApp.service.MeasurementsService;
import ru.anhimov.sensorApp.service.SensorService;
import ru.anhimov.sensorApp.util.MeasurementsValidator;
import ru.anhimov.sensorApp.util.SensorExistenceValidator;
import ru.anhimov.sensorApp.util.ValidationUtil;

import java.math.BigDecimal;
import java.util.List;

@WebMvcTest(MeasurementsController.class)
public class MeasurementsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeasurementsService measurementsService;

    @MockBean
    private SensorService sensorService;

    @MockBean
    private MeasurementsValidator measurementsValidator;

    @MockBean
    private SensorExistenceValidator sensorExistenceValidator;

    @MockBean
    private ValidationUtil validationUtil;

    @Test
    public void testIndex() throws Exception {
        when(measurementsService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/measurements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.measurements").isArray());

        verify(measurementsService, times(1)).findAll();
    }

    @Test
    public void testRegisterMeasurement() throws Exception {
        String measurementJson = "{ \"value\": 25.0, \"raining\": true, \"sensor\": { \"name\": \"Sensor1\" } }";

        doNothing().when(validationUtil).validateAndThrow(any(), any(), any(), any());

        mockMvc.perform(post("/measurements/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(measurementJson))
                .andExpect(status().isCreated());

        verify(measurementsService, times(1)).save(any());
    }

    @Test
    void testFindByName() throws Exception {
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setName("TestSensor");

        Sensor sensor = new Sensor();
        sensor.setName("TestSensor");

        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setValue(BigDecimal.valueOf(23,0));
        measurementDTO.setRaining(true);

        when(sensorService.convertToSensor(sensorDTO)).thenReturn(sensor);
        when(measurementsService.findMeasurementsBySensorName("TestSensor")).thenReturn(List.of(new Measurement()));
        when(measurementsService.convertToMeasurementDTO(any(Measurement.class))).thenReturn(measurementDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/measurements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"TestSensor\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.measurements").isArray())
                .andExpect(jsonPath("$.measurements[0].value").value(23.0))
                .andExpect(jsonPath("$.measurements[0].raining").value(true));

        verify(sensorService).convertToSensor(sensorDTO);
        verify(validationUtil).validateAndThrow(eq(sensorExistenceValidator), eq(sensor), any(), any());
        verify(measurementsService).findMeasurementsBySensorName("TestSensor");
    }

    @Test
    void testRainyDaysCount() throws Exception {
        Measurement rainyMeasurement = new Measurement();
        rainyMeasurement.setRaining(true);

        Measurement sunnyMeasurement = new Measurement();
        sunnyMeasurement.setRaining(false);

        when(measurementsService.findAll()).thenReturn(List.of(rainyMeasurement, sunnyMeasurement, rainyMeasurement));

        mockMvc.perform(MockMvcRequestBuilders.get("/measurements/rainyDaysCount"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));

        verify(measurementsService).findAll();
    }
}

