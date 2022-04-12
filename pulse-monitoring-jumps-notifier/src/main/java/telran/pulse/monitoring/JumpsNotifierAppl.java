package telran.pulse.monitoring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import telran.pulse.monitoring.dto.DoctorPatientData;
import telran.pulse.monitoring.dto.SensorJump;

import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
public class JumpsNotifierAppl {
    public static void main(String[] args) {
        SpringApplication.run(JumpsNotifierAppl.class, args);
    }

    @Value("{app.mail.subject:Critical pulse jump}")
    String subject;

    JavaMailSender jms;
    DataProviderClient client;

    public JumpsNotifierAppl(JavaMailSender jms, DataProviderClient client) {
        this.jms = jms;
        this.client = client;
    }

    @Bean
    Consumer<SensorJump> criticalJumpsConsumer() {
        return this::jumpsProcessing;
    }

    private void jumpsProcessing(SensorJump sensorJump) {
        log.trace("received sensor id {} previous value {} - cur value {} = jump {}",
                sensorJump.sensorId,
                sensorJump.prevValue,
                sensorJump.curValue,
                Math.abs(sensorJump.prevValue - sensorJump.curValue));
        DoctorPatientData data = client.getData(sensorJump.sensorId);
        log.debug("data received is {}", data.toString());
        sendMail(data, sensorJump);
    }

    private void sendMail(DoctorPatientData data, SensorJump sensorJump) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(data.email);
        mailMessage.setSubject(subject);
        mailMessage.setText(String.format("Dear %s,\nYour patient %s had the critical pulse jump\n" +
                "Previous pulse value %d; current pulse value %d\n",
                data.doctorName,
                data.patientName,
                sensorJump.prevValue,
                sensorJump.curValue));
        jms.send(mailMessage);
        log.debug("mail sent to {}", data.email);
    }
}
