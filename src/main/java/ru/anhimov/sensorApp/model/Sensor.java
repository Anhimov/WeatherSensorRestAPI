package ru.anhimov.sensorApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "sensor")
@Data
@NoArgsConstructor
public class Sensor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters.")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurements;
}
