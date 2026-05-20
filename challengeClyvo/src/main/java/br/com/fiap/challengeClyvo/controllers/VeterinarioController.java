package br.com.fiap.challengeClyvo.controllers;

import br.com.fiap.challengeClyvo.enums.UF;
import br.com.fiap.challengeClyvo.model.Veterinario;
import br.com.fiap.challengeClyvo.services.VeterinarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/veterinarios")
public class VeterinarioController {


    @Autowired
    private VeterinarioService veterinarioService;

    @PostMapping
    public ResponseEntity<Veterinario> salvar(@RequestBody @Valid Veterinario veterinario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioService.salvar(veterinario));
    }

    @GetMapping
    public ResponseEntity<List<Veterinario>> buscarTodos() {
        return ResponseEntity.ok(veterinarioService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veterinario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(veterinarioService.buscarPorId(id));
    }

    @GetMapping("/crmv/{numeroDeInscricao}")
    public ResponseEntity<Veterinario> buscarPorCrmv(@PathVariable int numeroDeInscricao) {
        return ResponseEntity.ok(veterinarioService.buscarPorCrmv(numeroDeInscricao));
    }

    @GetMapping("/area/{area}")
    public ResponseEntity<List<Veterinario>> buscarPorArea(@PathVariable String area) {
        return ResponseEntity.ok(veterinarioService.buscarPorArea(area));
    }

    @GetMapping("/uf/{uf}")
    public ResponseEntity<List<Veterinario>> buscarPorUf(@PathVariable UF uf) {
        return ResponseEntity.ok(veterinarioService.buscarPorUf(uf));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veterinario> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid Veterinario veterinario) {
        return ResponseEntity.ok(veterinarioService.atualizar(id, veterinario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        veterinarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
