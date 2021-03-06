package telran.pulse.monitoring.service;

import telran.pulse.monitoring.dto.VisitDto;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitsService {
    void addPatient(int patientId, String name);
    void addDoctor(String email, String name);
    void addVisit(int patientId, String email, LocalDateTime dateTime);
    List<VisitDto> getVisits(int patientId, LocalDateTime from, LocalDateTime to);
}
