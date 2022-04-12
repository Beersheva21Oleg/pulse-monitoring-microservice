package telran.pulse.monitoring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import telran.pulse.monitoring.dto.SensorJump;
import telran.pulse.monitoring.entities.JumpDoc;
import telran.pulse.monitoring.repo.JumpsRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
public class JumpsPopulatorAppl {

    public static void main(String[] args) {
        SpringApplication.run(JumpsPopulatorAppl.class, args);
    }

    @Bean
    Consumer<SensorJump> jumpConsumer() {
        return this::jumpsProcessing;
    }

    @Autowired
    JumpsRepository jumpsRepository;

    private void jumpsProcessing(SensorJump sensorJump) {
        log.trace("received sensor id {} previous value {} - cur value {} = jump {}",
                sensorJump.sensorId,
                sensorJump.prevValue,
                sensorJump.curValue,
                Math.abs(sensorJump.prevValue - sensorJump.curValue));

        JumpDoc document = JumpDoc.builder()
                .sensorId(sensorJump.sensorId)
                .prevValue(sensorJump.prevValue)
                .curValue(sensorJump.curValue)
                .dateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(sensorJump.timestamp), ZoneId.systemDefault()))
                .build();

        jumpsRepository.insert(document);
    }


}
