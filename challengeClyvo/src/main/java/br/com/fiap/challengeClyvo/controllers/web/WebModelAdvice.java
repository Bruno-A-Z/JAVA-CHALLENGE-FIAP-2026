package br.com.fiap.challengeClyvo.controllers.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

// Disponibiliza "isAdmin" para o menu esconder o que o VETERINARIO não pode acessar
@ControllerAdvice(basePackageClasses = WebModelAdvice.class)
public class WebModelAdvice {

    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
    }
}
