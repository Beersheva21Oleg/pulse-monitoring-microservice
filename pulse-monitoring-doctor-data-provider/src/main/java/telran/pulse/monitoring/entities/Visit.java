package telran.pulse.monitoring.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "doctor_email")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
