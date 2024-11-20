package ru.anhimov.sensorApp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SensorsResponse {
    private List<SensorDTO> sensors;

    public SensorsResponse(List<SensorDTO> sensors) {
        this.sensors = sensors;
    }
}
