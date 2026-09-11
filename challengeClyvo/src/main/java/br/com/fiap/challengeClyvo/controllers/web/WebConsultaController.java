package br.com.fiap.challengeClyvo.controllers.web;

import br.com.fiap.challengeClyvo.dto.request.ConsultaRequestDTO;
import br.com.fiap.challengeClyvo.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/consulta")
public class WebConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    public String listar(Model model, @RequestParam(defaultValue = "0") int pagina) {
        Pageable pageable = PageRequest.of(pagina, 10);
        model.addAttribute("consultas", consultaService.buscarTodas(pageable));
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