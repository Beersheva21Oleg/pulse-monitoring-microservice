package telran.pulse.monitoring.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    public String authToken;
    public String role;
}
