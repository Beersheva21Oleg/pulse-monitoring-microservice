package telran.pulse.monitoring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import telran.pulse.monitoring.dto.Sensor;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

@SpringBootApplication
@Slf4j
public class PulseSourceAppl {

    int count = 0;
    static ConfigurableApplicationContext ctx;

    public static void main(String[] args) {
        ctx = SpringApplication.run(PulseSourceAppl.class, args);
    }

    @Bean
    Supplier<Sensor> pulseSupplier() {
        return this::pulseRandomGeneration;
    }

    private Sensor pulseRandomGeneration() {
//        if (count > 100) {
//            ctx.close();
//        }
        int id = getRandomNumber(1, 10);
        int value = getRandomNumber(40, 250);
        Sensor sensor = new Sensor(id, value);
        log.debug("Sensor #{} with value {} has been sended", id, value);
        return sensor;
    }

    private int getRandomNumber(int from, int to) {
        return ThreadLocalRandom.current().nextInt(from, to);
    }
}
