package ru.anhimov.sensorApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.anhimov.sensorApp.model.Sensor;

import java.io.Serializable;

/**
 * DTO for {@link Sensor}
 */
@Data
@NoArgsConstructor
public class SensorDTO implements Serializable {
    @Size(message = "Name must be between 3 and 30 characters.", min = 3, max = 30)
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    public SensorDTO(String name) {
        this.name = name;
    }
}