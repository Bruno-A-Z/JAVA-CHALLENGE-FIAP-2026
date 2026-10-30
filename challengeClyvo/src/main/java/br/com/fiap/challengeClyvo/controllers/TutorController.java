package br.com.fiap.challengeClyvo.controllers;

import br.com.fiap.challengeClyvo.assemblers.TutorAssembler;
import br.com.fiap.challengeClyvo.dto.request.TutorRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.TutorResponseDTO;
import br.com.fiap.challengeClyvo.services.TutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Tutor", description = "Gerenciamento de tutores")
@RestController
@RequestMapping("/tutor")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorAssembler tutorAssembler;

    @Autowired
    private PagedResourcesAssembler<TutorResponseDTO> pagedAssembler;

    @Operation(summary = "Cria um tutor")
    @PostMapping
    public ResponseEntity<TutorResponseDTO> salvar(@RequestBody @Valid TutorRequestDTO tutor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tutorService.salvar(tutor));
    }

    @Operation(summary = "Lista todos os tutores")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<TutorResponseDTO>>> buscarTodos(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(pagedAssembler.toModel(tutorService.buscarTodos(pageable), tutorAssembler));
    }

    @Operation(summary = "Busca o Tutor pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TutorResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tutorAssembler.toModel(tutorService.buscarPorId(id)));
    }

    @Operation(summary = "Busca o Tutor pelo CPF")
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<EntityModel<TutorResponseDTO>> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(tutorAssembler.toModel(tutorService.buscarPorCpf(cpf)));
    }

    @Operation(summary = "Busca o Tutor pelo Nome")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<PagedModel<EntityModel<TutorResponseDTO>>> buscarPorNome(
            @PathVariable String nome,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(pagedAssembler.toModel(tutorService.buscarPorNome(nome, pageable), tutorAssembler));
    }

    @Operation(summary = "Atualiza um Tutor Existente pelo ID")
    @PutMapping("/{id}")
    public ResponseEntity<TutorResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid TutorRequestDTO tutor) {
        return ResponseEntity.ok(tutorService.atualizar(id, tutor));
    }

    @Operation(summary = "Deleta o Tutor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tutorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
