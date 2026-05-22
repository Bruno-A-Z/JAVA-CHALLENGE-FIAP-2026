package br.com.fiap.challengeClyvo.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(criarResposta(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    // Trata IllegalStateException — regras de negócio violadas
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(criarResposta(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    // Trata erros de validação do Bean Validation (@NotBlank, @NotNull, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException e) {
        Map<String, String> erros = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(erro ->
                erros.put(erro.getField(), erro.getDefaultMessage())
        );

        Map<String, Object> resposta = criarResposta(HttpStatus.BAD_REQUEST, "Erro de validação.");
        resposta.put("erros", erros);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    // Trata qualquer outra exceção não prevista
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(criarResposta(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor."));
    }

    // Monta o corpo padrão da resposta de erro - PARECIDO com um ToString, MAS para tratamento de erros
    private Map<String, Object> criarResposta(HttpStatus status, String mensagem) {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("status", status.value());
        resposta.put("mensagem", mensagem);
        resposta.put("timestamp", LocalDateTime.now());
        return resposta;
    }
}