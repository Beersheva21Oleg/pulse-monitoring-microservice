package telran.pulse.monitoring.entities;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "average_values")
@Builder
@Getter
public class SensorDoc {
    private int sensorId;
    private int value;
    private LocalDateTime dateTime;

}
