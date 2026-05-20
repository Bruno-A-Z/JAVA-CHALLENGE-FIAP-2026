package br.com.fiap.challengeClyvo.controllers;

import br.com.fiap.challengeClyvo.model.Pet;
import br.com.fiap.challengeClyvo.services.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/pets")
public class PetController {


    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<Pet> salvar(@RequestBody @Valid Pet pet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.salvar(pet));
    }

    @GetMapping
    public ResponseEntity<List<Pet>> buscarTodos() {
        return ResponseEntity.ok(petService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(petService.buscarPorId(id));
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Pet>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(petService.buscarPorNome(nome));
    }

    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<Pet>> buscarPorEspecie(@PathVariable String especie) {
        return ResponseEntity.ok(petService.buscarPorEspecie(especie));
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
