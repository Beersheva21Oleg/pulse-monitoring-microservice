package telran.pulse.monitoring.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
@Builder
@AllArgsConstructor
public class Visit {
    @Id
    @GeneratedValue
    private int id;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "doctor_email")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Visit() {

    }
}
