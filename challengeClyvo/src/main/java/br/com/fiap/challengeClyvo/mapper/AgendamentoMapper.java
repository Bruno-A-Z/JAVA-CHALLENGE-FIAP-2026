package br.com.fiap.challengeClyvo.mapper;

import br.com.fiap.challengeClyvo.dto.request.AgendamentoRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.AgendamentoResponseDTO;
import br.com.fiap.challengeClyvo.entity.Agendamento;
import br.com.fiap.challengeClyvo.entity.Pet;
import br.com.fiap.challengeClyvo.entity.Veterinario;

public class AgendamentoMapper {

    // Pet e Veterinario já vêm pelo service (buscados por id a partir do DTO)
    public static Agendamento toEntity(AgendamentoRequestDTO dto, Pet pet, Veterinario veterinario) {
        Agendamento agendamento = new Agendamento();
        agendamento.setDataHora(dto.getDataHora());
        agendamento.setPet(pet);
        agendamento.setVeterinario(veterinario);
        agendamento.setMotivo(dto.getMotivo());
        return agendamento;
    }

    public static AgendamentoResponseDTO toDTO(Agendamento agendamento) {
        return new AgendamentoResponseDTO(
                agendamento.getId(),
                agendamento.getDataHora(),
                agendamento.getPet().getId(),
                agendamento.getVeterinario().getId(),
                agendamento.getStatus(),
                agendamento.getMotivo()
        );
    }
}