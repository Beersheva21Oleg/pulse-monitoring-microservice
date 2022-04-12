package telran.pulse.monitoring.dto;

import javax.validation.constraints.Email;

public class DoctorDto {
    @Email
    public String email;
    public String name;
}
