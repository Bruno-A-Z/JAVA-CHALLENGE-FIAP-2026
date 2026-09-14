package br.com.fiap.challengeClyvo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Value("${app.security.admin.username}")
    private String adminUsername;

    @Value("${app.security.admin.password}")
    private String adminPassword;

    @Value("${app.security.vet.username}")
    private String vetUsername;

    @Value("${app.security.vet.password}")
    private String vetPassword;

    private final PasswordEncoder passwordEncoder;

    public AppUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(adminUsername)) {
            return User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .roles("ADMIN")
                    .build();
        }

        if (username.equals(vetUsername)) {
            return User.builder()
                    .username(vetUsername)
                    .password(passwordEncoder.encode(vetPassword))
                    .roles("VETERINARIO")
                    .build();
        }

        throw new UsernameNotFoundException("Usuário não encontrado.");
    }
}