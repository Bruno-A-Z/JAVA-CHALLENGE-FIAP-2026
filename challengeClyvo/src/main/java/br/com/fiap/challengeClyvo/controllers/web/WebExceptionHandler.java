package br.com.fiap.challengeClyvo.controllers.web;

import br.com.fiap.challengeClyvo.exceptions.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

// Erros de negócio nas telas viram uma página com UX
@ControllerAdvice(basePackageClasses = WebExceptionHandler.class)
public class WebExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleNotFound(EntityNotFoundException e) {
        return erro(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView handleRegraDeNegocio(IllegalStateException e) {
        return erro(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleIntegridade(DataIntegrityViolationException e) {
        return erro(HttpStatus.CONFLICT, "Operação não permitida: o registro está vinculado a outros dados (ex.: agendamentos).");
    }

    private ModelAndView erro(HttpStatus status, String mensagem) {
        ModelAndView mv = new ModelAndView("erro");
        mv.setStatus(status);
        mv.addObject("mensagem", mensagem);
        return mv;
    }
}
