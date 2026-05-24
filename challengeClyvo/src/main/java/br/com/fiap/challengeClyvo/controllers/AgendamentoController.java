package br.com.fiap.challengeClyvo.controllers;

import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import br.com.fiap.challengeClyvo.model.Agendamento;
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

import java.util.List;

@Tag(name = "Agendamento", description = "Gerenciamento do Agendadamento de Consultas")
@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {


    @Autowired
    private AgendamentoService agendamentoService;

    @Operation(summary = "Cria um Agendamento")
    @PostMapping
    public ResponseEntity<Agendamento> salvar(@RequestBody @Valid Agendamento agendamento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoService.salvar(agendamento));
    }

    @Operation(summary = "Lista Todos Agendamentos")
    @GetMapping
    public ResponseEntity<Page<Agendamento>> buscarTodos(
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(agendamentoService.buscarTodos(pageable));
    }

    @Operation(summary = "Busca Agendamento pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    @Operation(summary = "Busca Agendamento pelo Status")
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<Agendamento>> buscarPorStatus(
            @PathVariable StatusAgendamento status,
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(agendamentoService.buscarPorStatus(status, pageable));
    }

    @Operation(summary = "Busca Agendamento pelo ID do Veterinário")
    @GetMapping("/veterinario/{idVet}")
    public ResponseEntity<Page<Agendamento>> buscarPorVeterinario(
            @PathVariable Long idVet,
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(agendamentoService.buscarPorVeterinario(idVet, pageable));
    }

    @Operation(summary = "Busca Agendamento pelo ID do Pet")
    @GetMapping("/pet/{idPet}")
    public ResponseEntity<Page<Agendamento>> buscarPorPet(
            @PathVariable Long idPet,
            @PageableDefault(size = 10, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(agendamentoService.buscarPorPet(idPet, pageable));
    }

    @Operation(summary = "Cancela um agendaento existente pelo ID")
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Agendamento> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.cancelar(id));
    }
}
