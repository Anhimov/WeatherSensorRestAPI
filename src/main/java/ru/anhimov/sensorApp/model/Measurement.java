package ru.anhimov.sensorApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "measurement")
@Data
@NoArgsConstructor
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "\"value\"")
    @NotNull(message = "Value cannot be null.")
    @Min(message = "Value must be at least -100.", value = -100)
    @Max(message = "Value must be no more than 100.", value = 100)
    private BigDecimal value;

    @Column(name = "raining")
    @NotNull(message = "Raining cannot be null.")
    private Boolean raining;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    @NotNull(message = "Sensor cannot be null.")
    private Sensor sensor;

    @Column(name = "created_at")
    @NotNull(message = "CreatedBy cannot be null")
    private LocalDateTime createdAt;
}
