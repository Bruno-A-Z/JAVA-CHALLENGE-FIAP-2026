package br.com.fiap.challengeClyvo.controllers;

import br.com.fiap.challengeClyvo.dto.request.AgendamentoRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.AgendamentoResponseDTO;
import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import br.com.fiap.challengeClyvo.services.AgendamentoService;
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

@Tag(name = "Agendamento", description = "Gerenciamento do Agendadamento de Consultas")
@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Operation(summary = "Cria um Agendamento")
    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> salvar(@RequestBody @Valid AgendamentoRequestDTO agendamento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoService.salvar(agendamento));
    }

    @Operation(summary = "Lista Todos Agendamentos")
    @GetMapping
    public ResponseEntity<Page<AgendamentoResponseDTO>> buscarTodos(
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(agendamentoService.buscarTodos(pageable));
    }

    @Operation(summary = "Busca Agendamento pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    @Operation(summary = "Busca Agendamento pelo Status")
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<AgendamentoResponseDTO>> buscarPorStatus(
            @PathVariable StatusAgendamento status,
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(agendamentoService.buscarPorStatus(status, pageable));
    }

    @Operation(summary = "Busca Agendamento pelo ID do Veterinário")
    @GetMapping("/veterinario/{idVet}")
    public ResponseEntity<Page<AgendamentoResponseDTO>> buscarPorVeterinario(
            @PathVariable Long idVet,
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(agendamentoService.buscarPorVeterinario(idVet, pageable));
    }

    @Operation(summary = "Busca Agendamento pelo ID do Pet")
    @GetMapping("/pet/{idPet}")
    public ResponseEntity<Page<AgendamentoResponseDTO>> buscarPorPet(
            @PathVariable Long idPet,
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(agendamentoService.buscarPorPet(idPet, pageable));
    }

    @Operation(summary = "Cancela um agendaento existente pelo ID")
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<AgendamentoResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.cancelar(id));
    }
}
