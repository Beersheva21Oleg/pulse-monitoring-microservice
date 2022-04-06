package telran.pulse.monitoring.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SensorJump {

    public int sensorId;
    public int prevValue;
    public int curValue;
    public long timestamp;

    public SensorJump(int sensorId, int prevValue, int curValue) {
        this.sensorId = sensorId;
        this.prevValue = prevValue;
        this.curValue = curValue;
        timestamp = System.currentTimeMillis();
    }
}
