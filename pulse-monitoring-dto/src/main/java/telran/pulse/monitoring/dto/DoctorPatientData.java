package telran.pulse.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class DoctorPatientData {
    public String email;
    public String doctorName;
    public String patientName;

    @Override
    public String toString() {
        return "DoctorPatientData{" +
                "email='" + email + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", patientName='" + patientName + '\'' +
                '}';
    }
}
