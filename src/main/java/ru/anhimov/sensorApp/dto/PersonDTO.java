package ru.anhimov.sensorApp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PersonDTO implements Serializable {
    @Size(message = "Name must be between 2 and 50 characters.", min = 2, max = 50)
    @NotEmpty(message = "Name cannot be empty.")
    private String name;

    @Min(message = "Age must be at least 18.", value = 18)
    @Max(message = "Age must be no more than 65.", value = 65)
    private int age;

    @Email(message = "Email should be valid.")
    @NotEmpty(message = "Email cannot be empty.")
    private String email;
}