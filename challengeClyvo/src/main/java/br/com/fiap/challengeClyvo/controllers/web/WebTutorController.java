package br.com.fiap.challengeClyvo.controllers.web;

import br.com.fiap.challengeClyvo.dto.request.TutorRequestDTO;
import br.com.fiap.challengeClyvo.services.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/tutor")
public class WebTutorController {

    @Autowired
    private TutorService tutorService;

    @GetMapping
    public String listar(Model model, @RequestParam(defaultValue = "0") int pagina) {
        Pageable pageable = PageRequest.of(pagina, 10);
        model.addAttribute("tutores", tutorService.buscarTodos(pageable));
        return "tutor/listar";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("tutor", new TutorRequestDTO());
        model.addAttribute("modoEdicao", false);
        return "tutor/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("tutor") TutorRequestDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("modoEdicao", false);
            return "tutor/form";
        }
        tutorService.salvar(dto);
        return "redirect:/web/tutor";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        var tutor = tutorService.buscarPorId(id);

        TutorRequestDTO dto = new TutorRequestDTO();
        dto.setNome(tutor.getNome());
        dto.setIdade(tutor.getIdade());
        dto.setEndereco(tutor.getEndereco());
        dto.setTel(tutor.getTel());
        dto.setCpf(tutor.getCpf());

        model.addAttribute("tutor", dto);
        model.addAttribute("id", id);
        model.addAttribute("modoEdicao", true);
        return "tutor/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("tutor") TutorRequestDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("modoEdicao", true);
            return "tutor/form";
        }
        tutorService.atualizar(id, dto);
        return "redirect:/web/tutor";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id) {
        tutorService.deletar(id);
        return "redirect:/web/tutor";
    }
}