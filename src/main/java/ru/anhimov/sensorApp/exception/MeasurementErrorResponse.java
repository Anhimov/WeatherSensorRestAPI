package ru.anhimov.sensorApp.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@NoArgsConstructor
public class MeasurementErrorResponse {
    private String message;
    private LocalDateTime timestamp;
}