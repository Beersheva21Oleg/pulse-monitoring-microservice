package telran.pulse.monitoring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccountingManagement {

    private ConcurrentHashMap<String, Account> accounts;

    @Bean
    MapReactiveUserDetailsService getUserDetailsService() {
        accounts = new ConcurrentHashMap<>();
        accounts.put("admin@tel-ran.co.il",
                new Account("admin@tel-ran.co.il",
                        "$2a$10$0d.gqun7BTHuD1lNHDNWAujVXkHwcpZIXGiXb8oJvA/JbJjfKcrpm",
                        "ADMIN"));
        accounts.put("user@tel-ran.co.il",
                new Account("user@tel-ran.co.il",
                        "$2a$10$rSdI0lSvHmwhzOxLQ1olOujYO4gIGgRhst03Si3vKxtpASI/4W3Ni",
                        "USER"));

        ConcurrentHashMap<String, UserDetails> accountsList = new ConcurrentHashMap<>();

        accounts.forEach((key, value) -> accountsList.put(key,
                new User(
                        key,
                        value.getHashPassword(),
                        AuthorityUtils.createAuthorityList("ROLE_" + value.getRole())
                ))
        );
        return new MapReactiveUserDetailsService(accountsList);
    }

    public Account getAccount(String username) {
        return accounts.get(username);
    }

}
