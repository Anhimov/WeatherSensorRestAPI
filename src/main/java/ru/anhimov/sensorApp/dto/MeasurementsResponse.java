package ru.anhimov.sensorApp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MeasurementsResponse {
    private List<MeasurementDTO> measurements;

    public MeasurementsResponse(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }

}
