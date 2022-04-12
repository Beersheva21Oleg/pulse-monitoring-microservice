package telran.pulse.monitoring.entities;

import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "jumps")
@Getter
public class JumpDoc {

    @Indexed
    private int sensorId;
    private int prevValue;
    private int curValue;
    @Indexed
    private LocalDateTime dateTime;

}
