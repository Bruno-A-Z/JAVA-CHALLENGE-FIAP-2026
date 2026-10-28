package br.com.fiap.challengeClyvo.controllers.web;

import br.com.fiap.challengeClyvo.dto.response.AgendamentoResponseDTO;
import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import br.com.fiap.challengeClyvo.services.AgendamentoService;
import br.com.fiap.challengeClyvo.services.PetService;
import br.com.fiap.challengeClyvo.services.TutorService;
import br.com.fiap.challengeClyvo.services.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebHomeController {

    @Autowired
    private TutorService tutorService;

    @Autowired
    private PetService petService;

    @Autowired
    private VeterinarioService veterinarioService;

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private WebNomeResolver nomeResolver;

    @GetMapping("/")
    public String raiz() {
        return "redirect:/web";
    }

    @GetMapping("/web")
    public String inicio(Model model) {
        var umaLinha = PageRequest.of(0, 1);

        model.addAttribute("totalTutores", tutorService.buscarTodos(umaLinha).getTotalElements());
        model.addAttribute("totalPets", petService.buscarTodos(umaLinha).getTotalElements());
        model.addAttribute("totalVeterinarios", veterinarioService.buscarTodos(umaLinha).getTotalElements());

        var agendados = agendamentoService.buscarPorStatus(StatusAgendamento.AGENDADO,
                PageRequest.of(0, 5, Sort.by("dataHora")));
        List<AgendamentoResponseDTO> proximos = agendados.getContent();

        model.addAttribute("totalAgendados", agendados.getTotalElements());
        model.addAttribute("proximos", proximos);
        model.addAttribute("nomesPets", nomeResolver.nomesDosPets(proximos));
        model.addAttribute("nomesVeterinarios", nomeResolver.nomesDosVeterinarios(proximos));
        return "home";
    }
}