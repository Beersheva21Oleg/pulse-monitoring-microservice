package telran.pulse.monitoring.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import telran.pulse.monitoring.dto.*;
import telran.pulse.monitoring.security.*;
import java.util.Base64;

@RestController
@AllArgsConstructor
public class LoginController {

    private PasswordEncoder passwordEncoder;
    private AccountingManagement accountingManagement;

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginData loginData) {
        Account account = accountingManagement.getAccount(loginData.email);

        if (account != null && passwordEncoder.matches(loginData.password, account.getHashPassword())) {
            return ResponseEntity.ok(getResponse(loginData, account));
        }

        return ResponseEntity.badRequest().body("Wrong credentials");

    }

    private LoginResponse getResponse(LoginData loginData, Account account) {
        return new LoginResponse(getToken(loginData), account.getRole());
    }

    private String getToken(LoginData loginData) {
        byte[] code = String.format("%s:%s", loginData.email, loginData.password).getBytes();
        return "Basic " + Base64.getEncoder().encodeToString(code);
    }

}