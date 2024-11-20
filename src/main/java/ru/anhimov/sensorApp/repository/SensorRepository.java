package ru.anhimov.sensorApp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.anhimov.sensorApp.model.Sensor;

import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByName(String name);
}
