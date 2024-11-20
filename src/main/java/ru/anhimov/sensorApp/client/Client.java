package ru.anhimov.sensorApp.client;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ru.anhimov.sensorApp.dto.MeasurementDTO;
import ru.anhimov.sensorApp.dto.MeasurementsResponse;
import ru.anhimov.sensorApp.dto.SensorDTO;
import ru.anhimov.sensorApp.dto.SensorsResponse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Client {
    private static final String ADD_MEASUREMENT_URI = "http://localhost:8088/measurements/add";
    private static final String GET_MEASUREMENTS_URI = "http://localhost:8088/measurements";
    private static final String GET_SENSOR_URI = "http://localhost:8088/sensors";
    private static final String REGISTER_SENSOR_URI = "http://localhost:8088/sensors/register";
    private static final String NEW_SENSOR_NAME = "Samara";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

//        registerSensor(REGISTER_SENSOR_URI, NEW_SENSOR_NAME, restTemplate);

        List<SensorDTO> allSensors = getAllSensors(GET_SENSOR_URI, restTemplate);
//
//        List<HashMap<String, Object>> measurements = create1000Measurements(allSensors);
//
//        sendMeasurements(ADD_MEASUREMENT_URI, measurements,restTemplate);
//
//        List<Double> allMeasurements = getMeasurementsFromServer(GET_MEASUREMENTS_URI, restTemplate, null);
//        drawChart(allMeasurements);


        String sensorName = allSensors.get(0).getName();
        List<BigDecimal> currentSensorMeasurements = getMeasurementsFromServer(GET_MEASUREMENTS_URI, restTemplate, sensorName);
        drawChart(currentSensorMeasurements);
    }

    private static List<BigDecimal> getMeasurementsFromServer(String url,
                                                              RestTemplate restTemplate,
                                                              String name) {

        MeasurementsResponse jsonResponse;
        if (nonNull(name)) {
            final HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            HashMap<String, Object> jsonData = new HashMap<>();
            jsonData.put("name", name);
            HttpEntity<HashMap<String, Object>> request = new HttpEntity<>(jsonData, httpHeaders);

            jsonResponse = restTemplate.postForObject(url, request, MeasurementsResponse.class);
        } else {
            jsonResponse = restTemplate.getForObject(url, MeasurementsResponse.class);
        }

        if (isNull(jsonResponse) || isNull(jsonResponse.getMeasurements())) {
            return Collections.emptyList();
        }

        return jsonResponse.getMeasurements()
                .stream()
                .map(MeasurementDTO::getValue)
                .collect(Collectors.toList());
    }

    private static void drawChart(List<BigDecimal> measurements) {
        double[] xData = IntStream.range(0, measurements.size()).asDoubleStream().toArray();
        double[] yData = measurements.stream().mapToDouble(BigDecimal::doubleValue).toArray();

        XYChart chart = QuickChart.getChart("Temperatures", "time", "value", "seriesName",
                xData, yData);
        new SwingWrapper(chart).displayChart();
    }

    private static void registerSensor(String registerSensorUri, String sensorName, RestTemplate restTemplate) {
        HashMap<String, Object> jsonData = new HashMap<>();
        jsonData.put("name", sensorName);
        makePostRequestWithJSONData(registerSensorUri, jsonData, restTemplate);
    }

    private static void sendMeasurements(String addMeasurementURI, List<HashMap<String, Object>> jsonData, RestTemplate restTemplate) {
        jsonData.forEach(json -> {
            makePostRequestWithJSONData(addMeasurementURI, json, restTemplate);
        });
    }

    private static void makePostRequestWithJSONData(String registerSensorUri, HashMap<String, Object> jsonData, RestTemplate restTemplate) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<HashMap<String, Object>> request = new HttpEntity<>(jsonData, httpHeaders);
        try {
            restTemplate.postForObject(registerSensorUri, request, String.class);
            System.out.println("Successfully send request to " + registerSensorUri);
        } catch (Exception e) {
            System.out.println("Failed to send request to " + registerSensorUri);
            System.out.println(e.getMessage());
        }
    }

    private static List<HashMap<String, Object>> create1000Measurements(List<SensorDTO> allSensors) {
        Random random = new Random();
        return IntStream.range(0, 100).parallel()
                .mapToObj(i -> {
                    HashMap<String, Object> jsonData = new HashMap<>();

                    double randValue = random.nextDouble(15, 40);
                    double roundedRandValue = Math.round(randValue * 100.0) / 100.0;
                    jsonData.put("value", roundedRandValue);

                    boolean randIsRaining = random.nextBoolean();
                    jsonData.put("raining", randIsRaining);

                    String randSensorName = allSensors.get(random.nextInt(allSensors.size())).getName();
                    jsonData.put("sensor", Map.of("name", randSensorName));

                    return jsonData;
                })
                .collect(Collectors.toList());
    }

    private static List<SensorDTO> getAllSensors(String getSensorURI, RestTemplate restTemplate) {
        try {
            SensorsResponse response = restTemplate.getForObject(getSensorURI, SensorsResponse.class);

            if (nonNull(response) && nonNull(response.getSensors())) {
                return response.getSensors();
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
