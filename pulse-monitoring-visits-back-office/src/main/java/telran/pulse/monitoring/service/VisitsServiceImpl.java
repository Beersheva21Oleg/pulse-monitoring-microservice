package telran.pulse.monitoring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import telran.pulse.monitoring.dto.VisitDto;
import telran.pulse.monitoring.entities.Doctor;
import telran.pulse.monitoring.entities.Patient;
import telran.pulse.monitoring.entities.Visit;
import telran.pulse.monitoring.repo.DoctorRepository;
import telran.pulse.monitoring.repo.PatientRepository;
import telran.pulse.monitoring.repo.VisitRepository;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class VisitsServiceImpl implements VisitsService{

    DoctorRepository doctorRepository;
    PatientRepository patientRepository;
    VisitRepository visitRepository;

    @Override
    @Transactional
    public void addPatient(int patientId, String name) {
        if(patientRepository.existsById(patientId)){
            throw new IllegalArgumentException("Duplicated patient id");
        }
        Patient patient = Patient.builder().id(patientId).name(name).build();
        patientRepository.save(patient);
    }

    @Override
    @Transactional
    public void addDoctor(String email, String name) {
        if (doctorRepository.existsById(email)){
            throw new IllegalArgumentException("Duplicated email address");
        }
        Doctor doctor = Doctor.builder().email(email).name(name).build();
        doctorRepository.save(doctor);
    }

    @Override
    @Transactional
    public void addVisit(int patientId, String email, LocalDateTime dateTime) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient == null) {
            throw new IllegalArgumentException(String.format("patient with id %d not found",
                    patientId));
        }
        Doctor doctor = doctorRepository.findById(email).orElse(null);
        if (doctor == null) {
            throw new IllegalArgumentException(String.format("Doctor with email %s doesn't exist",
                    email));
        }
        Visit visit = Visit.builder().date(dateTime).patient(patient).doctor(doctor).build();
        visitRepository.save(visit);
    }

    @Override
    public List<VisitDto> getVisits(int patientId, LocalDateTime from, LocalDateTime to) {
        return visitRepository.getVisitsPatientDates(patientId, from, to);
    }


}
