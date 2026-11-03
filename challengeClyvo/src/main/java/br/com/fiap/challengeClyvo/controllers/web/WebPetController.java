package br.com.fiap.challengeClyvo.controllers.web;

import br.com.fiap.challengeClyvo.dto.request.PetRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.AgendamentoResponseDTO;
import br.com.fiap.challengeClyvo.dto.response.PetResponseDTO;
import br.com.fiap.challengeClyvo.dto.response.TutorResponseDTO;
import br.com.fiap.challengeClyvo.services.AgendamentoService;
import br.com.fiap.challengeClyvo.services.PetService;
import br.com.fiap.challengeClyvo.services.TutorService;
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
@RequestMapping("/web/pet")
public class WebPetController {

    @Autowired
    private PetService petService;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private WebNomeResolver nomeResolver;

    @GetMapping
    public String listar(Model model, @RequestParam(defaultValue = "0") int pagina) {
        Pageable pageable = PageRequest.of(pagina, 10);
        Page<PetResponseDTO> pets = petService.buscarTodos(pageable);

        // Tutores de cada pet da página (aparecem ao clicar na linha do pet)
        Map<Long, List<TutorResponseDTO>> tutoresPorPet = new HashMap<>();
        pets.getContent().forEach(pet -> tutoresPorPet.put(pet.getId(), petService.buscarTutoresDoPet(pet.getId())));

        // Ultimos agendamentos de cada pet da pagina (relacao pet <-> agendamento <-> veterinario)
        Map<Long, List<AgendamentoResponseDTO>> agendamentosPorPet = new HashMap<>();
        pets.getContent().forEach(pet -> agendamentosPorPet.put(pet.getId(),
                agendamentoService.buscarPorPet(pet.getId(),
                        PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "dataHora"))).getContent()));

        List<AgendamentoResponseDTO> todos = agendamentosPorPet.values().stream().flatMap(List::stream).toList();

        model.addAttribute("pets", pets);
        model.addAttribute("tutoresPorPet", tutoresPorPet);
        model.addAttribute("agendamentosPorPet", agendamentosPorPet);
        model.addAttribute("nomesVeterinarios", nomeResolver.nomesDosVeterinarios(todos));
        return "pet/listar";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("pet", new PetRequestDTO());
        model.addAttribute("modoEdicao", false);
        return "pet/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("pet") PetRequestDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("modoEdicao", false);
            return "pet/form";
        }
        petService.salvar(dto);
        return "redirect:/web/pet";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        var pet = petService.buscarPorId(id);

        PetRequestDTO dto = new PetRequestDTO();
        dto.setNome(pet.getNome());
        dto.setEspecie(pet.getEspecie());
        dto.setRaca(pet.getRaca());
        dto.setCor(pet.getCor());
        dto.setIdade(pet.getIdade());
        dto.setPeso(pet.getPeso());

        model.addAttribute("pet", dto);
        carregarEdicao(model, id);
        return "pet/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("pet") PetRequestDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            carregarEdicao(model, id);
            return "pet/form";
        }
        petService.atualizar(id, dto);
        return "redirect:/web/pet";
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable Long id) {
        petService.deletar(id);
        return "redirect:/web/pet";
    }

    @PostMapping("/{idPet}/tutor")
    public String vincularTutor(@PathVariable Long idPet, @RequestParam String cpf) {
        petService.adicionarTutorPorCpf(idPet, cpf);
        return "redirect:/web/pet/" + idPet + "/editar";
    }

    @PostMapping("/{idPet}/tutor/{idTutor}")
    public String adicionarTutor(@PathVariable Long idPet, @PathVariable Long idTutor) {
        petService.adicionarTutor(idPet, idTutor);
        return "redirect:/web/pet/" + idPet + "/editar";
    }

    @PostMapping("/{idPet}/tutor/{idTutor}/remover")
    public String removerTutor(@PathVariable Long idPet, @PathVariable Long idTutor) {
        petService.removerTutor(idPet, idTutor);
        return "redirect:/web/pet/" + idPet + "/editar";
    }

    private void carregarEdicao(Model model, Long idPet) {
        List<TutorResponseDTO> vinculados = petService.buscarTutoresDoPet(idPet);
        List<Long> idsVinculados = vinculados.stream().map(TutorResponseDTO::getId).toList();

        List<TutorResponseDTO> disponiveis = tutorService.buscarTodos(PageRequest.of(0, 200)).getContent().stream()
                .filter(t -> !idsVinculados.contains(t.getId()))
                .toList();

        model.addAttribute("id", idPet);
        model.addAttribute("modoEdicao", true);
        model.addAttribute("tutoresVinculados", vinculados);
        model.addAttribute("tutoresDisponiveis", disponiveis);
        model.addAttribute("podeVincular", vinculados.size() < PetService.LIMITE_TUTORES);
        model.addAttribute("limiteTutores", PetService.LIMITE_TUTORES);
    }
}
