package br.com.fiap.challengeClyvo.controllers.web;

import br.com.fiap.challengeClyvo.dto.response.AgendamentoResponseDTO;
import br.com.fiap.challengeClyvo.services.PetService;
import br.com.fiap.challengeClyvo.services.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    public Map<Long, String> nomesDosVeterinarios(Collection<AgendamentoResponseDTO> agendamentos) {
        Map<Long, String> nomes = new HashMap<>();
        agendamentos.forEach(ag -> nomes.computeIfAbsent(ag.getIdVeterinario(), id -> veterinarioService.buscarPorId(id).getNome()));
        return nomes;
    }
}