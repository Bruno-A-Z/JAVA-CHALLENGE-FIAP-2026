package br.com.fiap.challengeClyvo.controllers;

import br.com.fiap.challengeClyvo.model.Consulta;
import br.com.fiap.challengeClyvo.services.ConsultaService;
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

@Tag(name = "Consulta")
@RestController
@RequestMapping("/consulta")
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
    public ResponseEntity<Page<Consulta>> buscarTodas(
            @PageableDefault(size = 10, sort = "dataRealizacao") Pageable pageable) {
        return ResponseEntity.ok(consultaService.buscarTodas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    @GetMapping("/pet/{idPet}")
    public ResponseEntity<Page<Consulta>> buscarPorPet(
            @PathVariable Long idPet,
            @PageableDefault(size = 10, sort = "dataRealizacao") Pageable pageable) {
        return ResponseEntity.ok(consultaService.buscarPorPet(idPet, pageable));
    }

    @GetMapping("/veterinario/{idVet}")
    public ResponseEntity<Page<Consulta>> buscarPorVeterinario(
            @PathVariable Long idVet,
            @PageableDefault(size = 10, sort = "dataRealizacao") Pageable pageable) {
        return ResponseEntity.ok(consultaService.buscarPorVeterinario(idVet, pageable));
    }

    @PatchMapping("/{id}/observacoes")
    public ResponseEntity<Consulta> atualizarObservacoes(
            @PathVariable Long id,
            @RequestBody String observacoes) {
        return ResponseEntity.ok(consultaService.atualizarObservacoes(id, observacoes));
    }
}
