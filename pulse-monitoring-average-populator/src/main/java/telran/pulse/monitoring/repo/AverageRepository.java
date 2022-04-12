package telran.pulse.monitoring.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import telran.pulse.monitoring.entities.SensorDoc;

public interface AverageRepository extends MongoRepository<SensorDoc, ObjectId> {
}
