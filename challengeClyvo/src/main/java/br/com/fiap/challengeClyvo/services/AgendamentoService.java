package br.com.fiap.challengeClyvo.services;


import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import br.com.fiap.challengeClyvo.model.Agendamento;
import br.com.fiap.challengeClyvo.model.Pet;
import br.com.fiap.challengeClyvo.model.Veterinario;
import br.com.fiap.challengeClyvo.repository.AgendamentoRepository;
import br.com.fiap.challengeClyvo.repository.PetRepository;
import br.com.fiap.challengeClyvo.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Agendamento> buscarTodos() {
        return agendamentoRepository.findAll();
    }

    public Agendamento buscarPorId(Long idAgendamento) {
        return agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado."));
    }

    public List<Agendamento> buscarPorStatus(StatusAgendamento status) {
        return agendamentoRepository.findByStatus(status);
    }

    public List<Agendamento> buscarPorVeterinario(Long idVet) {
        return agendamentoRepository.findByVeterinarioId(idVet);
    }

    public List<Agendamento> buscarPorPet(Long idPet) {
        return agendamentoRepository.findByPetId(idPet);
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