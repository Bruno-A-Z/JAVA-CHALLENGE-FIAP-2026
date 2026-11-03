package br.com.fiap.challengeClyvo.controllers.web;

import br.com.fiap.challengeClyvo.dto.response.AgendamentoResponseDTO;
import br.com.fiap.challengeClyvo.dto.response.TutorResponseDTO;
import br.com.fiap.challengeClyvo.services.PetService;
import br.com.fiap.challengeClyvo.services.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WebNomeResolver {

    @Autowired
    private PetService petService;

    @Autowired
    private VeterinarioService veterinarioService;

    public Map<Long, String> nomesDosPets(Collection<AgendamentoResponseDTO> agendamentos) {
        Map<Long, String> nomes = new HashMap<>();
        agendamentos.forEach(ag -> nomes.computeIfAbsent(ag.getIdPet(), id -> petService.buscarPorId(id).getNome()));
        return nomes;
    }

    // Tutores de cada pet, separados por virgula (relacao pet <-> tutor)
    public Map<Long, String> tutoresDosPets(Collection<AgendamentoResponseDTO> agendamentos) {
        Map<Long, String> nomes = new HashMap<>();
        agendamentos.forEach(ag -> nomes.computeIfAbsent(ag.getIdPet(), id ->
                petService.buscarTutoresDoPet(id).stream()
                        .map(TutorResponseDTO::getNome)
                        .collect(Collectors.joining(", "))));
        return nomes;
    }

    public Map<Long, String> nomesDosVeterinarios(Collection<AgendamentoResponseDTO> agendamentos) {
        Map<Long, String> nomes = new HashMap<>();
        agendamentos.forEach(ag -> nomes.computeIfAbsent(ag.getIdVeterinario(), id -> veterinarioService.buscarPorId(id).getNome()));
        return nomes;
    }
}