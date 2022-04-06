package telran.pulse.monitoring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import telran.pulse.monitoring.dto.SensorJump;

import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
public class JumpsNotifierAppl {
    public static void main(String[] args) {
        SpringApplication.run(JumpsNotifierAppl.class, args);
    }

    @Bean
    Consumer<SensorJump> criticalJumpsConsumer() {
        return this::jumpsProcessing;
    }

    private void jumpsProcessing(SensorJump sensorJump) {
        log.trace("received sensor id {} previous value {} - cur value {} = jump {}",
                sensorJump.sensorId,
                sensorJump.prevValue,
                sensorJump.curValue,
                Math.abs(sensorJump.prevValue - sensorJump.curValue));
    }
}
