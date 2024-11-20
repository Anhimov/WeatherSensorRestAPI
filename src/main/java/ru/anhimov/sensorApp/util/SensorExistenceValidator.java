package ru.anhimov.sensorApp.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.anhimov.sensorApp.model.Sensor;
import ru.anhimov.sensorApp.service.SensorService;

@Component
public class SensorExistenceValidator implements Validator {
    private final SensorService sensorService;

    public SensorExistenceValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Sensor.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (!sensorService.findByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "nameNotFound", "Sensor with this name does not exist");
        }
    }
}
