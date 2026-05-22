package br.com.fiap.challengeClyvo.controllers;

import br.com.fiap.challengeClyvo.model.Tutor;
import br.com.fiap.challengeClyvo.services.TutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tutor")
@RestController
@RequestMapping("/tutor")
public class TutorController {


    @Autowired
    private TutorService tutorService;

    @Operation(summary = "Cria um tutor")
    @PostMapping
    public ResponseEntity<Tutor> salvar(@RequestBody @Valid Tutor tutor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tutorService.salvar(tutor));
    }

    @Operation(summary = "Lista todos os tutores")
    @GetMapping
    public ResponseEntity<Page<Tutor>> buscarTodos(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(tutorService.buscarTodos(pageable));
    }

    @Operation(summary = "Busca o Tutor pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Tutor> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tutorService.buscarPorId(id));
    }

    @Operation(summary = "Busca o Tutor pelo CPF")
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Tutor> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(tutorService.buscarPorCpf(cpf));
    }

    @Operation(summary = "Busca o Tutor pelo Nome")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Page<Tutor>> buscarPorNome(
            @PathVariable String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(tutorService.buscarPorNome(nome, pageable));
    }

    @Operation(summary = "Atualiza um Tutor Existente pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<Tutor> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid Tutor tutor) {
        return ResponseEntity.ok(tutorService.atualizar(id, tutor));
    }

    @Operation(summary = "Deleta o Tutor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tutorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}