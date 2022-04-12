package telran.pulse.monitoring.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "doctors")
@Builder
@AllArgsConstructor
public class Doctor {
    @Id
    private String email;
    private String name;

    public Doctor() {

    }
}
