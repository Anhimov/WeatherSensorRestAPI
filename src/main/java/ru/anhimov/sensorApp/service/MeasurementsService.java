package ru.anhimov.sensorApp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.anhimov.sensorApp.dto.MeasurementDTO;
import ru.anhimov.sensorApp.model.Measurement;
import ru.anhimov.sensorApp.model.Sensor;
import ru.anhimov.sensorApp.repository.MeasurementsRepository;
import ru.anhimov.sensorApp.repository.SensorRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final ModelMapper modelMapper;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, ModelMapper modelMapper, SensorRepository sensorRepository) {
        this.measurementsRepository = measurementsRepository;
        this.modelMapper = modelMapper;
        this.sensorRepository = sensorRepository;
    }

    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement) {
        enrichMeasurement(measurement);
        measurementsRepository.save(measurement);
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement m) {
        return modelMapper.map(m, MeasurementDTO.class);
    }

    public Measurement convertToMeasurement(MeasurementDTO dto) {
        return modelMapper.map(dto, Measurement.class);
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());
        measurement.setSensor(sensorRepository.findByName(measurement.getSensor().getName()).get());
    }

    public List<Measurement> findMeasurementsBySensorName(String name) {
        Sensor sensor = sensorRepository
                .findByName(name)
                .orElseThrow(() -> new RuntimeException("Sensor not found"));

        return measurementsRepository.findBySensorName(sensor);
    }
}
