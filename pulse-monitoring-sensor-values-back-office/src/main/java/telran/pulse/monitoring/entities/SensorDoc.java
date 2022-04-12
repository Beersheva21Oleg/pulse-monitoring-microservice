package telran.pulse.monitoring.entities;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "average_values")
@Getter
public class SensorDoc {
    private int sensorId;
    private int value;
    private LocalDateTime dateTime;
}
