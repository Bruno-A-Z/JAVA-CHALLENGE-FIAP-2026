package br.com.fiap.challengeClyvo.controllers.web;

import br.com.fiap.challengeClyvo.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

// Erros de negócio nas telas viram uma página com UX
@Slf4j
@ControllerAdvice(basePackageClasses = WebExceptionHandler.class)
public class WebExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleNotFound(EntityNotFoundException e) {
        log.warn("Recurso não encontrado (tela): {}", e.getMessage());
        return erro(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView handleRegraDeNegocio(IllegalStateException e) {
        log.warn("Regra de negócio violada (tela): {}", e.getMessage());
        return erro(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleIntegridade(DataIntegrityViolationException e) {
        log.warn("Violação de integridade (tela): {}", e.getMostSpecificCause().getMessage());
        return erro(HttpStatus.CONFLICT, "Operação não permitida: o registro está vinculado a outros dados (ex.: agendamentos).");
    }

    private ModelAndView erro(HttpStatus status, String mensagem) {
        ModelAndView mv = new ModelAndView("erro");
        mv.setStatus(status);
        mv.addObject("mensagem", mensagem);
        return mv;
    }
}