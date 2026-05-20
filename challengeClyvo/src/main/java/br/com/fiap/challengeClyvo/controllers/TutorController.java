package br.com.fiap.challengeClyvo.controllers;

import br.com.fiap.challengeClyvo.model.Tutor;
import br.com.fiap.challengeClyvo.services.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tutores")
public class TutorController {


    @Autowired
    private TutorService tutorService;

    @PostMapping
    public ResponseEntity<Tutor> salvar(@RequestBody @Valid Tutor tutor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tutorService.salvar(tutor));
    }

    @GetMapping
    public ResponseEntity<List<Tutor>> buscarTodos() {
        return ResponseEntity.ok(tutorService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutor> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tutorService.buscarPorId(id));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Tutor> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(tutorService.buscarPorCpf(cpf));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Tutor>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(tutorService.buscarPorNome(nome));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tutor> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid Tutor tutor) {
        return ResponseEntity.ok(tutorService.atualizar(id, tutor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tutorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}