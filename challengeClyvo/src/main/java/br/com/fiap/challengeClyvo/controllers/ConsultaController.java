package br.com.fiap.challengeClyvo.controllers;

import br.com.fiap.challengeClyvo.assemblers.ConsultaAssembler;
import br.com.fiap.challengeClyvo.dto.request.ConsultaRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.ConsultaResponseDTO;
import br.com.fiap.challengeClyvo.services.ConsultaService;
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

@Tag(name = "Consulta", description = "Gerenciamento de Consultas")
@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private ConsultaAssembler consultaAssembler;

    @Autowired
    private PagedResourcesAssembler<ConsultaResponseDTO> pagedAssembler;

    @Operation(summary = "Atualiza o Status da Consulta para 'Realizada' criando diagnostico e histórico")
    @PostMapping("/agendamento/{idAgendamento}")
    public ResponseEntity<ConsultaResponseDTO> realizar(
            @PathVariable Long idAgendamento,
            @RequestBody @Valid ConsultaRequestDTO consulta) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(consultaService.realizar(idAgendamento, consulta));
    }

    @Operation(summary = "Lista todas as Consultas")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ConsultaResponseDTO>>> buscarTodas(
            @PageableDefault(size = 10, sort = "dataRealizacao") Pageable pageable) {
        return ResponseEntity.ok(pagedAssembler.toModel(consultaService.buscarTodas(pageable), consultaAssembler));
    }

    @Operation(summary = "Busca Consulta por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ConsultaResponseDTO>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consultaAssembler.toModel(consultaService.buscarPorId(id)));
    }

    @Operation(summary = "Busca Consulta pelo ID do Pet")
    @GetMapping("/pet/{idPet}")
    public ResponseEntity<PagedModel<EntityModel<ConsultaResponseDTO>>> buscarPorPet(
            @PathVariable Long idPet,
            @PageableDefault(size = 10, sort = "dataRealizacao") Pageable pageable) {
        return ResponseEntity.ok(pagedAssembler.toModel(consultaService.buscarPorPet(idPet, pageable), consultaAssembler));
    }

    @Operation(summary = "Busca Consulta pelo ID do Veterinário que realizou")
    @GetMapping("/veterinario/{idVet}")
    public ResponseEntity<PagedModel<EntityModel<ConsultaResponseDTO>>> buscarPorVeterinario(
            @PathVariable Long idVet,
            @PageableDefault(size = 10, sort = "dataRealizacao") Pageable pageable) {
        return ResponseEntity.ok(pagedAssembler.toModel(consultaService.buscarPorVeterinario(idVet, pageable), consultaAssembler));
    }

    @Operation(summary = "Atualiza Observações da Consulta do Pet")
    @PatchMapping("/{id}/observacoes")
    public ResponseEntity<ConsultaResponseDTO> atualizarObservacoes(
            @PathVariable Long id,
            @RequestBody String observacoes) {
        return ResponseEntity.ok(consultaService.atualizarObservacoes(id, observacoes));
    }
}
