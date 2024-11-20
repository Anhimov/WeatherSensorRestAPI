package ru.anhimov.sensorApp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.anhimov.sensorApp.model.Measurement;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Measurement}
 */
@Data
@NoArgsConstructor
public class MeasurementDTO implements Serializable {
    @NotNull(message = "Value cannot be null.")
    @Min(message = "Value must be at least -100.", value = -100)
    @Max(message = "Value must be no more than 100.", value = 100)
    private BigDecimal value;

    @NotNull(message = "Raining cannot be null.")
    private boolean isRaining;

    @NotNull(message = "Sensor cannot be null.")
    private SensorDTO sensor;
}