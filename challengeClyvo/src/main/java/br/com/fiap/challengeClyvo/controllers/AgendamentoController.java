package br.com.fiap.challengeClyvo.controllers;

import br.com.fiap.challengeClyvo.assemblers.AgendamentoAssembler;
import br.com.fiap.challengeClyvo.dto.request.AgendamentoRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.AgendamentoResponseDTO;
import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import br.com.fiap.challengeClyvo.services.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Agendamento", description = "Gerenciamento do Agendadamento de Consultas")
@RestController
@RequestMapping("/agendamento")
@RequiredArgsConstructor
public class AgendamentoController {

    private AgendamentoService agendamentoService;
    private AgendamentoAssembler agendamentoAssembler;
    private PagedResourcesAssembler<AgendamentoResponseDTO> pagedAssembler;

    @Operation(summary = "Cria um Agendamento")
    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> salvar(@RequestBody @Valid AgendamentoRequestDTO agendamento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoService.salvar(agendamento));
    }

    @Operation(summary = "Lista Todos Agendamentos")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<AgendamentoResponseDTO>>> buscarTodos(
            @ParameterObject @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(pagedAssembler.toModel(agendamentoService.buscarTodos(pageable), agendamentoAssembler));
    }

    @Operation(summary = "Busca Agendamento pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AgendamentoResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoAssembler.toModel(agendamentoService.buscarPorId(id)));
    }

    @Operation(summary = "Busca Agendamento pelo Status")
    @GetMapping("/status/{status}")
    public ResponseEntity<PagedModel<EntityModel<AgendamentoResponseDTO>>> buscarPorStatus(
            @PathVariable StatusAgendamento status,
            @ParameterObject @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(pagedAssembler.toModel(agendamentoService.buscarPorStatus(status, pageable), agendamentoAssembler));
    }

    @Operation(summary = "Busca Agendamento pelo ID do Veterinário")
    @GetMapping("/veterinario/{idVet}")
    public ResponseEntity<PagedModel<EntityModel<AgendamentoResponseDTO>>> buscarPorVeterinario(
            @PathVariable Long idVet,
            @ParameterObject @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(pagedAssembler.toModel(agendamentoService.buscarPorVeterinario(idVet, pageable), agendamentoAssembler));
    }

    @Operation(summary = "Busca Agendamento pelo ID do Pet")
    @GetMapping("/pet/{idPet}")
    public ResponseEntity<PagedModel<EntityModel<AgendamentoResponseDTO>>> buscarPorPet(
            @PathVariable Long idPet,
            @ParameterObject @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(pagedAssembler.toModel(agendamentoService.buscarPorPet(idPet, pageable), agendamentoAssembler));
    }

    @Operation(summary = "Cancela um agendaento existente pelo ID")
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<AgendamentoResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.cancelar(id));
    }
}
