package br.com.fiap.challengeClyvo.controllers;

import br.com.fiap.challengeClyvo.model.Consulta;
import br.com.fiap.challengeClyvo.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/consultas")
public class ConsultaController {


    @Autowired
    private ConsultaService consultaService;

    @PostMapping("/agendamento/{idAgendamento}")
    public ResponseEntity<Consulta> realizar(
            @PathVariable Long idAgendamento,
            @RequestBody @Valid Consulta consulta) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(consultaService.realizar(idAgendamento, consulta));
    }

    @GetMapping
    public ResponseEntity<List<Consulta>> buscarTodas() {
        return ResponseEntity.ok(consultaService.buscarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    @GetMapping("/pet/{idPet}")
    public ResponseEntity<List<Consulta>> buscarPorPet(@PathVariable Long idPet) {
        return ResponseEntity.ok(consultaService.buscarPorPet(idPet));
    }

    @GetMapping("/veterinario/{idVet}")
    public ResponseEntity<List<Consulta>> buscarPorVeterinario(@PathVariable Long idVet) {
        return ResponseEntity.ok(consultaService.buscarPorVeterinario(idVet));
    }

    @PatchMapping("/{id}/observacoes")
    public ResponseEntity<Consulta> atualizarObservacoes(
            @PathVariable Long id,
            @RequestBody String observacoes) {
        return ResponseEntity.ok(consultaService.atualizarObservacoes(id, observacoes));
    }
}
