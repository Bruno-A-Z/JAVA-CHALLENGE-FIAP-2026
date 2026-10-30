package br.com.fiap.challengeClyvo.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebAcessoNegadoController {

    @RequestMapping("/web/acesso-negado")
    public String acessoNegado() {
        return "acesso-negado";
    }
}