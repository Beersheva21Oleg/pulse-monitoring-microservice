package telran.pulse.monitoring.repo;

import org.springframework.data.repository.CrudRepository;
import telran.pulse.monitoring.entities.SensorRedis;

public interface SensorRepository extends CrudRepository<SensorRedis, Integer> {
}
