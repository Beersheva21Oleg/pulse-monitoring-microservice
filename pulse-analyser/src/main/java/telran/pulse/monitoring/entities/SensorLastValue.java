package telran.pulse.monitoring.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
public class SensorLastValue {
    @Id
    private int id;
    int lastValue;

    public SensorLastValue(int id) {
        this.id = id;
    }

    public int getLastValue() {
        return lastValue;
    }

    public void addLastValue(int lastValue) {
        this.lastValue = lastValue;
    }
}
