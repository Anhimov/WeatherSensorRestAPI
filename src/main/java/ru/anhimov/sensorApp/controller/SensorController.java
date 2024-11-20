package ru.anhimov.sensorApp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.anhimov.sensorApp.dto.SensorDTO;
import ru.anhimov.sensorApp.dto.SensorsResponse;
import ru.anhimov.sensorApp.exception.SensorNotValidException;
import ru.anhimov.sensorApp.model.Sensor;
import ru.anhimov.sensorApp.service.SensorService;
import ru.anhimov.sensorApp.util.SensorValidator;
import ru.anhimov.sensorApp.util.ValidationUtil;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator validator;
    private final ValidationUtil validationUtil;

    @Autowired
    public SensorController(SensorService sensorService,
                            SensorValidator validator,
                            ValidationUtil validationUtil) {

        this.sensorService = sensorService;
        this.validator = validator;
        this.validationUtil = validationUtil;
    }

    @GetMapping
    public SensorsResponse index() {
        return new SensorsResponse(sensorService.findAll()
                .stream()
                .map(sensorService::convertToSensorDTO)
                .collect(Collectors.toList()));
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                     BindingResult bindingResult) {

        Sensor sensor = sensorService.convertToSensor(sensorDTO);
        validationUtil.validateAndThrow(validator, sensor, bindingResult, new SensorNotValidException());

        sensorService.register(sensor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
