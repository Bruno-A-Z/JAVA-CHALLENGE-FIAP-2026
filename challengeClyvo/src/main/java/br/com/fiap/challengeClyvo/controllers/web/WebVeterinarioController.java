package br.com.fiap.challengeClyvo.controllers.web;

import br.com.fiap.challengeClyvo.dto.CrmvDTO;
import br.com.fiap.challengeClyvo.dto.request.VeterinarioRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.AgendamentoResponseDTO;
import br.com.fiap.challengeClyvo.dto.response.VeterinarioResponseDTO;
import br.com.fiap.challengeClyvo.enums.UF;
import br.com.fiap.challengeClyvo.services.AgendamentoService;
import br.com.fiap.challengeClyvo.services.VeterinarioService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web/veterinario")
public class WebVeterinarioController {

    @Autowired
    private VeterinarioService veterinarioService;

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private WebNomeResolver nomeResolver;

    @GetMapping
    public String listar(Model model, @RequestParam(defaultValue = "0") int pagina) {
        Pageable pageable = PageRequest.of(pagina, 10);
        Page<VeterinarioResponseDTO> veterinarios = veterinarioService.buscarTodos(pageable);

        // Ultimos agendamentos de cada veterinario da pagina (aparecem ao clicar na linha)
        Map<Long, List<AgendamentoResponseDTO>> agendamentosPorVet = new HashMap<>();
        veterinarios.getContent().forEach(v -> agendamentosPorVet.put(v.getId(),
                agendamentoService.buscarPorVeterinario(v.getId(),
                        PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "dataHora"))).getContent()));

        List<AgendamentoResponseDTO> todos = agendamentosPorVet.values().stream().flatMap(List::stream).toList();

        model.addAttribute("veterinarios", veterinarios);
        model.addAttribute("agendamentosPorVet", agendamentosPorVet);
        model.addAttribute("nomesPets", nomeResolver.nomesDosPets(todos));
        return "veterinario/listar";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        VeterinarioRequestDTO dto = new VeterinarioRequestDTO();
        dto.setCrmv(new CrmvDTO());
        model.addAttribute("veterinario", dto);
        model.addAttribute("ufs", UF.values());
        model.addAttribute("modoEdicao", false);
        return "veterinario/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("veterinario") VeterinarioRequestDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ufs", UF.values());
            model.addAttribute("modoEdicao", false);
            return "veterinario/form";
        }
        veterinarioService.salvar(dto);
        return "redirect:/web/veterinario";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        var vet = veterinarioService.buscarPorId(id);

        VeterinarioRequestDTO dto = new VeterinarioRequestDTO();
        dto.setNome(vet.getNome());
        dto.setArea(vet.getArea());
        dto.setCrmv(new CrmvDTO(vet.getCrmv().getUf(), vet.getCrmv().getNumeroDeInscricao()));

        model.addAttribute("veterinario", dto);
        model.addAttribute("ufs", UF.values());
        model.addAttribute("id", id);
        model.addAttribute("modoEdicao", true);
        return "veterinario/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("veterinario") VeterinarioRequestDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ufs", UF.values());
            model.addAttribute("id", id);
            model.addAttribute("modoEdicao", true);
            return "veterinario/form";
        }
        veterinarioService.atualizar(id, dto);
        return "redirect:/web/veterinario";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id) {
        veterinarioService.deletar(id);
        return "redirect:/web/veterinario";
    }
}