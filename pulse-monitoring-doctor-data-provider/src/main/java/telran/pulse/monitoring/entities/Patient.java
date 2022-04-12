package telran.pulse.monitoring.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "patients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Patient {
    @Id
    private Integer id;
    private String name;
}
