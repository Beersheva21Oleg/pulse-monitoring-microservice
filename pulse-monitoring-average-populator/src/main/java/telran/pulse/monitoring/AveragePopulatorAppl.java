package telran.pulse.monitoring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import telran.pulse.monitoring.dto.Sensor;
import telran.pulse.monitoring.entities.SensorDoc;
import telran.pulse.monitoring.repo.AverageRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
public class AveragePopulatorAppl {
    public static void main(String[] args) {
        SpringApplication.run(AveragePopulatorAppl.class, args);
    }

    @Autowired
    AverageRepository averageRepository;

    @Bean
    Consumer<Sensor> averageConsumer() {
        return this::averageProcessing;
    }

    private void averageProcessing(Sensor sensor) {
        log.debug("received sensor id {} with average value {}", sensor.id, sensor.value);

        SensorDoc document = SensorDoc.builder()
                .sensorId(sensor.id)
                .value(sensor.value)
                .dateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(sensor.timestamp), ZoneId.systemDefault()))
                .build();

        averageRepository.insert(document);
    }
}
