package telran.pulse.monitoring.entities;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "doctors")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    private String email;
    private String name;
}
