package br.com.fiap.challengeClyvo.controllers;

import br.com.fiap.challengeClyvo.dto.request.PetRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.PetResponseDTO;
import br.com.fiap.challengeClyvo.services.PetService;
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

@Tag(name = "Pet", description = "Gerenciamento de pets")
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Operation(summary = "Cria um Pet")
    @PostMapping
    public ResponseEntity<PetResponseDTO> salvar(@RequestBody @Valid PetRequestDTO pet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.salvar(pet));
    }

    @Operation(summary = "Lista todos os pets")
    @GetMapping
    public ResponseEntity<Page<PetResponseDTO>> buscarTodos(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(petService.buscarTodos(pageable));
    }

    @Operation(summary = "Busca o Pet pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(petService.buscarPorId(id));
    }

    @Operation(summary = "Busca o Pet Pelo Nome")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<Page<PetResponseDTO>> buscarPorNome(
            @PathVariable String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(petService.buscarPorNome(nome, pageable));
    }

    @Operation(summary = "Busca o Pet pela Espécie")
    @GetMapping("/especie/{especie}")
    public ResponseEntity<Page<PetResponseDTO>> buscarPorEspecie(
            @PathVariable String especie,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(petService.buscarPorEspecie(especie, pageable));
    }

    @Operation(summary = "Busca o Pet por Tutor")
    @GetMapping("/tutor/{idTutor}")
    public ResponseEntity<List<PetResponseDTO>> buscarPorTutor(@PathVariable Long idTutor) {
        return ResponseEntity.ok(petService.buscarPorTutor(idTutor));
    }

    @Operation(summary = "Adiciona um tutor ao Pet")
    @PostMapping("/{idPet}/tutor/{idTutor}")
    public ResponseEntity<PetResponseDTO> adicionarTutor(
            @PathVariable Long idPet,
            @PathVariable Long idTutor) {
        return ResponseEntity.ok(petService.adicionarTutor(idPet, idTutor));
    }

    @Operation(summary = "Remove o tutor do Pet pelo ID")
    @DeleteMapping("/{idPet}/tutor/{idTutor}")
    public ResponseEntity<PetResponseDTO> removerTutor(
            @PathVariable Long idPet,
            @PathVariable Long idTutor) {
        return ResponseEntity.ok(petService.removerTutor(idPet, idTutor));
    }

    @Operation(summary = "Atualiza um Pet existente")
    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PetRequestDTO pet) {
        return ResponseEntity.ok(petService.atualizar(id, pet));
    }

    @Operation(summary = "Deleta um Pet pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        petService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
