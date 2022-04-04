package telran.pulse.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Sensor {
    public int id;
    public int value;
    public int seqNum;
    public long timestamp;

    public Sensor(int id, int value, int seqNum) {
        this.id = id;
        this.value = value;
        this.seqNum = seqNum;
        timestamp = System.currentTimeMillis();
    }
}
