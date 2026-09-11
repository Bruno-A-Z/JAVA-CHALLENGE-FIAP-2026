package br.com.fiap.challengeClyvo.controllers.web;

import br.com.fiap.challengeClyvo.dto.request.AgendamentoRequestDTO;
import br.com.fiap.challengeClyvo.services.AgendamentoService;
import br.com.fiap.challengeClyvo.services.PetService;
import br.com.fiap.challengeClyvo.services.VeterinarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/agendamento")
public class WebAgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private PetService petService;

    @Autowired
    private VeterinarioService veterinarioService;

    @GetMapping
    public String listar(Model model, @RequestParam(defaultValue = "0") int pagina) {
        Pageable pageable = PageRequest.of(pagina, 10);
        model.addAttribute("agendamentos", agendamentoService.buscarTodos(pageable));
        return "agendamento/listar";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("agendamento", new AgendamentoRequestDTO());
        model.addAttribute("pets", petService.buscarTodos(PageRequest.of(0, 100)).getContent());
        model.addAttribute("veterinarios", veterinarioService.buscarTodos(PageRequest.of(0, 100)).getContent());
        return "agendamento/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("agendamento") AgendamentoRequestDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pets", petService.buscarTodos(PageRequest.of(0, 100)).getContent());
            model.addAttribute("veterinarios", veterinarioService.buscarTodos(PageRequest.of(0, 100)).getContent());
            return "agendamento/form";
        }
        agendamentoService.salvar(dto);
        return "redirect:/web/agendamento";
    }

    @PostMapping("/{id}/cancelar")
    public String cancelar(@PathVariable Long id) {
        agendamentoService.cancelar(id);
        return "redirect:/web/agendamento";
    }
}