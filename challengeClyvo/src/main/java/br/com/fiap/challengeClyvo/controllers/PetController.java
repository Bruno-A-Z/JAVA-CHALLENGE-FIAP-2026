package br.com.fiap.challengeClyvo.controllers;

import br.com.fiap.challengeClyvo.model.Pet;
import br.com.fiap.challengeClyvo.services.PetService;
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

@Tag(name = "Pet")
@RestController
@RequestMapping("/pet")
public class PetController {


    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<Pet> salvar(@RequestBody @Valid Pet pet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.salvar(pet));
    }

    @GetMapping
    public ResponseEntity<Page<Pet>> buscarTodos(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(petService.buscarTodos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(petService.buscarPorId(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Page<Pet>> buscarPorNome(
            @PathVariable String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(petService.buscarPorNome(nome, pageable));
    }

    @GetMapping("/especie/{especie}")
    public ResponseEntity<Page<Pet>> buscarPorEspecie(
            @PathVariable String especie,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(petService.buscarPorEspecie(especie, pageable));
    }

    @GetMapping("/tutor/{idTutor}")
    public ResponseEntity<List<Pet>> buscarPorTutor(@PathVariable Long idTutor) {
        return ResponseEntity.ok(petService.buscarPorTutor(idTutor));
    }

    @PostMapping("/{idPet}/tutores/{idTutor}")
    public ResponseEntity<Pet> adicionarTutor(
            @PathVariable Long idPet,
            @PathVariable Long idTutor) {
        return ResponseEntity.ok(petService.adicionarTutor(idPet, idTutor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid Pet pet) {
        return ResponseEntity.ok(petService.atualizar(id, pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        petService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
