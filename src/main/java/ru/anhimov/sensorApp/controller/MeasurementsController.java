package ru.anhimov.sensorApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.anhimov.sensorApp.dto.MeasurementDTO;
import ru.anhimov.sensorApp.dto.MeasurementsResponse;
import ru.anhimov.sensorApp.dto.SensorDTO;
import ru.anhimov.sensorApp.exception.MeasurementNotValidException;
import ru.anhimov.sensorApp.exception.SensorNotValidException;
import ru.anhimov.sensorApp.model.Measurement;
import ru.anhimov.sensorApp.model.Sensor;
import ru.anhimov.sensorApp.service.MeasurementsService;
import ru.anhimov.sensorApp.service.SensorService;
import ru.anhimov.sensorApp.util.MeasurementsValidator;
import ru.anhimov.sensorApp.util.SensorExistenceValidator;
import ru.anhimov.sensorApp.util.ValidationUtil;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final MeasurementsValidator measurementsValidator;
    private final SensorExistenceValidator sensorExistenceValidator;
    private final SensorService sensorService;
    private final ValidationUtil validationUtil;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService,
                                  MeasurementsValidator measurementsValidator,
                                  SensorService sensorService,
                                  ValidationUtil validationUtil,
                                  SensorExistenceValidator sensorExistenceValidator) {

        this.measurementsService = measurementsService;
        this.measurementsValidator = measurementsValidator;
        this.sensorService = sensorService;
        this.validationUtil = validationUtil;
        this.sensorExistenceValidator = sensorExistenceValidator;
    }

    @GetMapping
    public MeasurementsResponse index() {
        return new MeasurementsResponse(measurementsService
                .findAll()
                .stream()
                .map(measurementsService::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @PostMapping()
    public MeasurementsResponse findByName(@RequestBody @Valid SensorDTO sensorDTO,
                                           BindingResult bindingResult) {

        Sensor sensor = sensorService.convertToSensor(sensorDTO);
        validationUtil.validateAndThrow(sensorExistenceValidator, sensor, bindingResult, new SensorNotValidException());

        return new MeasurementsResponse(measurementsService
                .findMeasurementsBySensorName(sensorDTO.getName())
                .stream()
                .map(measurementsService::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rainyDaysCount")
    public Long rainyDaysCount() {
        return measurementsService.findAll().stream()
                .filter(Measurement::getRaining)
                .count();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> registerMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                          BindingResult bindingResult) {

        Measurement measurement = measurementsService.convertToMeasurement(measurementDTO);
        validationUtil.validateAndThrow(measurementsValidator, measurement, bindingResult, new MeasurementNotValidException());

        measurementsService.save(measurement);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
