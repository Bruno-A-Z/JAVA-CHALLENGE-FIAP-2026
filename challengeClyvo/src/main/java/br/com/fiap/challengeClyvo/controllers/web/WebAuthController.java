package br.com.fiap.challengeClyvo.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebAuthController {

    @GetMapping("/web/login")
    public String paginaLogin() {
        return "login";
    }
}