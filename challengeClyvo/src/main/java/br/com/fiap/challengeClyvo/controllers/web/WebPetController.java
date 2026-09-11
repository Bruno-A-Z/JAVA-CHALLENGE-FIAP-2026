package br.com.fiap.challengeClyvo.controllers.web;

import br.com.fiap.challengeClyvo.dto.request.PetRequestDTO;
import br.com.fiap.challengeClyvo.services.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/pet")
public class WebPetController {

    @Autowired
    private PetService petService;

    @GetMapping
    public String listar(Model model, @RequestParam(defaultValue = "0") int pagina) {
        Pageable pageable = PageRequest.of(pagina, 10);
        model.addAttribute("pets", petService.buscarTodos(pageable));
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
        model.addAttribute("id", id);
        model.addAttribute("modoEdicao", true);
        return "pet/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("pet") PetRequestDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("modoEdicao", true);
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
}