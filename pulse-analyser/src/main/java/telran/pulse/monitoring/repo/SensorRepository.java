package telran.pulse.monitoring.repo;

import org.springframework.data.repository.CrudRepository;
import telran.pulse.monitoring.entities.SensorLastValue;

public interface SensorRepository extends CrudRepository<SensorLastValue, Integer> {
}
