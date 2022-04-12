package telran.pulse.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Sensor {
    public int id;
    public int value;
    public long timestamp;

    public Sensor(int id, int value) {
        this.id = id;
        this.value = value;
        timestamp = System.currentTimeMillis();
    }
}
