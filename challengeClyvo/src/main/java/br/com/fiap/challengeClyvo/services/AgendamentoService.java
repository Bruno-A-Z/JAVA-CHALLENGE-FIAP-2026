package br.com.fiap.challengeClyvo.services;


import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import br.com.fiap.challengeClyvo.exceptions.EntityNotFoundException;
import br.com.fiap.challengeClyvo.model.Agendamento;
import br.com.fiap.challengeClyvo.model.Pet;
import br.com.fiap.challengeClyvo.model.Veterinario;
import br.com.fiap.challengeClyvo.repository.AgendamentoRepository;
import br.com.fiap.challengeClyvo.repository.PetRepository;
import br.com.fiap.challengeClyvo.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class AgendamentoService {


    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    public Agendamento salvar(Agendamento agendamento) {
        Pet pet = petRepository.findById(agendamento.getPet().getIdPet())
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado."));

        Veterinario vet = veterinarioRepository.findById(agendamento.getVeterinario().getIdVet())
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado."));

        // Verifica se o vet já tem agendamento no mesmo horário
        List<Agendamento> conflitoHorario = agendamentoRepository
                .findByVeterinarioIdAndDataHora(vet.getIdVet(), agendamento.getDataHora());

        if (!conflitoHorario.isEmpty()) {
            throw new IllegalStateException("Veterinário já possui agendamento nesse horário.");
        }

        agendamento.setPet(pet);
        agendamento.setVeterinario(vet);
        agendamento.setStatus(StatusAgendamento.AGENDADO);
        return agendamentoRepository.save(agendamento);
    }

    public Page<Agendamento> buscarTodos(Pageable pageable) {
        return agendamentoRepository.findAll(pageable);
    }

    public Agendamento buscarPorId(Long idAgendamento) {
        return agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado."));
    }

    public Page<Agendamento> buscarPorStatus(StatusAgendamento status, Pageable pageable) {
        return agendamentoRepository.findByStatus(status, pageable);
    }

    public Page<Agendamento> buscarPorVeterinario(Long idVet, Pageable pageable) {
        return agendamentoRepository.findByVeterinarioId(idVet, pageable);
    }

    public Page<Agendamento> buscarPorPet(Long idPet, Pageable pageable) {
        return agendamentoRepository.findByPetId(idPet, pageable);
    }

    public List<Agendamento> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return agendamentoRepository.findByDataHoraBetween(inicio, fim);
    }

    public Agendamento cancelar(Long idAgendamento) {
        Agendamento agendamento = buscarPorId(idAgendamento);

        if (agendamento.getStatus() == StatusAgendamento.REALIZADO) {
            throw new IllegalStateException("Não é possível cancelar uma consulta já realizada.");
        }

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        return agendamentoRepository.save(agendamento);
    }
}