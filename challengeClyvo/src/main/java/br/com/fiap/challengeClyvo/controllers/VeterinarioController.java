package br.com.fiap.challengeClyvo.controllers;

import br.com.fiap.challengeClyvo.enums.UF;
import br.com.fiap.challengeClyvo.model.Veterinario;
import br.com.fiap.challengeClyvo.services.VeterinarioService;
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

@Tag(name = "Veterinario")
@RestController
@RequestMapping("/veterinario")
public class VeterinarioController {


    @Autowired
    private VeterinarioService veterinarioService;

    @PostMapping
    public ResponseEntity<Veterinario> salvar(@RequestBody @Valid Veterinario veterinario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioService.salvar(veterinario));
    }

    @GetMapping
    public ResponseEntity<Page<Veterinario>> buscarTodos(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(veterinarioService.buscarTodos(pageable));
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
    public ResponseEntity<Page<Veterinario>> buscarPorArea(
            @PathVariable String area,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(veterinarioService.buscarPorArea(area, pageable));
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
