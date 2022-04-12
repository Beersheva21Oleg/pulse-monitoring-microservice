package telran.pulse.monitoring.dto;

import java.time.LocalDateTime;

public interface VisitDto {
    public int getPatientId();
    public String getPatientName();
    public String getDoctorName();
    public LocalDateTime getVisitDate();
}
