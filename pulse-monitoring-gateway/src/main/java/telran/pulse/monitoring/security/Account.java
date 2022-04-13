package telran.pulse.monitoring.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Account {
    private final String username;
    private String hashPassword;
    private String role;

}
