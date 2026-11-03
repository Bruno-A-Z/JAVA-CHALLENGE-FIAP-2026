package br.com.fiap.challengeClyvo.controllers.web;

import br.com.fiap.challengeClyvo.dto.request.ConsultaRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.AgendamentoResponseDTO;
import br.com.fiap.challengeClyvo.dto.response.ConsultaResponseDTO;
import br.com.fiap.challengeClyvo.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/web/consulta")
public class WebConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private WebNomeResolver nomeResolver;

    @GetMapping
    public String listar(Model model, @RequestParam(defaultValue = "0") int pagina) {
        Pageable pageable = PageRequest.of(pagina, 10, Sort.by(Sort.Direction.DESC, "dataRealizacao"));
        Page<ConsultaResponseDTO> consultas = consultaService.buscarTodas(pageable);

        List<AgendamentoResponseDTO> agendamentos = consultas.getContent().stream()
                .map(ConsultaResponseDTO::getAgendamento)
                .filter(Objects::nonNull)
                .toList();

        model.addAttribute("consultas", consultas);
        model.addAttribute("nomesPets", nomeResolver.nomesDosPets(agendamentos));
        model.addAttribute("nomesVeterinarios", nomeResolver.nomesDosVeterinarios(agendamentos));
        model.addAttribute("nomesTutores", nomeResolver.tutoresDosPets(agendamentos));
        return "consulta/listar";
    }

    @GetMapping("/agendamento/{idAgendamento}/realizar")
    public String realizarForm(@PathVariable Long idAgendamento, Model model) {
        model.addAttribute("consulta", new ConsultaRequestDTO());
        model.addAttribute("idAgendamento", idAgendamento);
        return "consulta/form";
    }

    @PostMapping("/agendamento/{idAgendamento}")
    public String realizar(@PathVariable Long idAgendamento, @Valid @ModelAttribute("consulta") ConsultaRequestDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("idAgendamento", idAgendamento);
            return "consulta/form";
        }
        consultaService.realizar(idAgendamento, dto);
        return "redirect:/web/consulta";
    }

    @PostMapping("/{id}/observacoes")
    public String atualizarObservacoes(@PathVariable Long id, @RequestParam String observacoes) {
        consultaService.atualizarObservacoes(id, observacoes);
        return "redirect:/web/consulta";
    }
}