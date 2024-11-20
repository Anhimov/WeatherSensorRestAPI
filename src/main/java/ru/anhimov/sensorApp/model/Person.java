package ru.anhimov.sensorApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Person")
@Data
@NoArgsConstructor
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name cannot be empty.")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters.")
    private String name;

    @Column(name = "age")
    @Min(value = 18, message = "Age must be at least 18.")
    @Max(value = 65, message = "Age must be no more than 65.")
    private int age;

    @Column(name = "email")
    @Email(message = "Email should be valid.")
    @NotEmpty(message = "Email cannot be empty.")
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    @NotEmpty(message = "CreatedBy cannot be empty")
    private String createdBy;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
