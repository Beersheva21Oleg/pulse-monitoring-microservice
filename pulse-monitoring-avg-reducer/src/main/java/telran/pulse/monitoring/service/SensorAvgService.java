package telran.pulse.monitoring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;
import telran.pulse.monitoring.dto.Sensor;
import telran.pulse.monitoring.entities.SensorList;
import telran.pulse.monitoring.repo.SensorRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Consumer;

@Service
@ManagedResource
@Slf4j
public class SensorAvgService {

    SensorRepository sensorRepository;
    StreamBridge streamBridge;

    public SensorAvgService(SensorRepository sensorRepository, StreamBridge streamBridge) {
        this.sensorRepository = sensorRepository;
        this.streamBridge = streamBridge;
    }

    Instant timestamp = Instant.now();

    @Value("${app.period.reduction:60000}")
    long reducingPeriod;

    @ManagedOperation
    public long getReducingPeriod() {
        return reducingPeriod;
    }

    @ManagedOperation
    public void setReducingPeriod(long reducingPeriod) {
        this.reducingPeriod = reducingPeriod;
    }

    @Value("${app.size.reduction:100}")
    int reducingSize;

    @ManagedOperation
    public int getReducingSize() {
        return reducingSize;
    }

    @ManagedOperation
    public void setReducingSize(int reducingSize) {
        this.reducingSize = reducingSize;
    }

    @Bean
    Consumer<Sensor> pulseConsumer() {
        return this::pulseAvgProcessing;
    }

    private void pulseAvgProcessing(Sensor sensor) {
        log.trace("received sensor id {}, value {}", sensor.id, sensor.value);
        SensorList sensorList = sensorRepository.findById(sensor.id).orElse(null);
        if (sensorList == null) {
            sensorList = new SensorList(sensor.id);
        }
        List<Integer> listValues = sensorList.getListValues();
        listValues.add(sensor.value);
        if (checkAverageProcessing(listValues.size())) {
            averageProcessing(listValues, sensor.id);
        }
        sensorRepository.save(sensorList);
    }

    private void averageProcessing(List<Integer> listValues, int id) {
        double avg = listValues.stream().mapToInt(x -> x).average().getAsDouble();
        listValues.clear();
        streamBridge.send("avg-values-out-0", new Sensor(id, (int) avg));
        log.debug("sensor id {}, average value {} has send to average topic", id, (int) avg);
        timestamp = Instant.now();
    }

    private boolean checkAverageProcessing(int valuesSize) {
        return ChronoUnit.MILLIS.between(timestamp, Instant.now()) > reducingPeriod
                || valuesSize > reducingSize;
    }
}
