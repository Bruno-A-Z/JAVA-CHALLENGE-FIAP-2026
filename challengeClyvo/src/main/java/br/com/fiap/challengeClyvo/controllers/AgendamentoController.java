package br.com.fiap.challengeClyvo.controllers;

import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import br.com.fiap.challengeClyvo.model.Agendamento;
import br.com.fiap.challengeClyvo.services.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {


    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<Agendamento> salvar(@RequestBody @Valid Agendamento agendamento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoService.salvar(agendamento));
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> buscarTodos() {
        return ResponseEntity.ok(agendamentoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Agendamento>> buscarPorStatus(@PathVariable StatusAgendamento status) {
        return ResponseEntity.ok(agendamentoService.buscarPorStatus(status));
    }

    @GetMapping("/veterinario/{idVet}")
    public ResponseEntity<List<Agendamento>> buscarPorVeterinario(@PathVariable Long idVet) {
        return ResponseEntity.ok(agendamentoService.buscarPorVeterinario(idVet));
    }

    @GetMapping("/pet/{idPet}")
    public ResponseEntity<List<Agendamento>> buscarPorPet(@PathVariable Long idPet) {
        return ResponseEntity.ok(agendamentoService.buscarPorPet(idPet));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Agendamento> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.cancelar(id));
    }
}
