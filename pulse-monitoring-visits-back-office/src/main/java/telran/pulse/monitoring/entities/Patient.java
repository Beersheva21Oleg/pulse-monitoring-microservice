package telran.pulse.monitoring.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "patients")
@Builder
@AllArgsConstructor
public class Patient {
    @Id
    private Integer id;
    private String name;

    public Patient() {

    }
}
