package ru.anhimov.sensorApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.anhimov.sensorApp.model.Measurement;
import ru.anhimov.sensorApp.model.Sensor;

import java.util.List;

public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
    @Query("select m from Measurement m where m.sensor = :sensor")
    List<Measurement> findBySensorName(@Param("sensor") Sensor sensor);
}
