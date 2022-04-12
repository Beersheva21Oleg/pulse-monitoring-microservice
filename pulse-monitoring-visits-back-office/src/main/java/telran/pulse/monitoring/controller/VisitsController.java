package telran.pulse.monitoring.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.*;
import org.springframework.web.bind.annotation.*;
import telran.pulse.monitoring.dto.DoctorDto;
import telran.pulse.monitoring.dto.PatientDto;
import telran.pulse.monitoring.dto.VisitDto;
import telran.pulse.monitoring.service.VisitsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/visits")
@AllArgsConstructor
public class VisitsController {
    VisitsService visitsService;

    @PostMapping("/patients")
    PatientDto addPatient(@RequestBody PatientDto patientDto) {
        visitsService.addPatient(patientDto.patientId, patientDto.patientName);
        return patientDto;
    }

    @PostMapping("/doctors")
    DoctorDto addDoctor(@RequestBody DoctorDto doctorDto) {
        visitsService.addDoctor(doctorDto.email, doctorDto.name);
        return doctorDto;
    }

    @PostMapping
    Map<String, Object> addVisit(@RequestBody Map<String, Object> visit) {
        visitsService.addVisit((Integer) visit.get("patientId"),
                (String) visit.get("email"),
                LocalDateTime.parse((String) visit.get("date"), DateTimeFormatter.ISO_DATE_TIME));
        return visit;
    }

    @GetMapping("/{patientId}")
    List<VisitDto> getVisitsPatientDates(@PathVariable int patientId,
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from) {
        return visitsService.getVisits(patientId, from, to);
    }
}
