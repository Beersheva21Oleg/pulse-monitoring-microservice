package telran.pulse.monitoring.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;

@RedisHash
public class SensorRedis {
    @Id
    private int id;
    private ArrayList<Integer> values = new ArrayList<>();

    public SensorRedis(int id) {
        this.id = id;
    }

    public int getLastValue() {
        return values.get(values.size() - 1);
    }

    public int addCurrentValue(int curValue) {
        values.add(curValue);
        return values.size() - 1;
    }
}
