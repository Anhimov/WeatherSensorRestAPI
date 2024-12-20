package ru.anhimov.sensorApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.anhimov.sensorApp.model.Measurement;
import ru.anhimov.sensorApp.service.SensorService;

@Component
public class MeasurementsValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public MeasurementsValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Measurement.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if (measurement.getSensor() == null) {
            errors.rejectValue("sensor", "sensorNull", "Sensor must be provided");
            return;
        }

        if (sensorService.findByName(measurement.getSensor().getName()).isEmpty()) {
            errors.rejectValue("sensor", "sensorNotExists", "Sensor does not exist");
        }
    }
}
