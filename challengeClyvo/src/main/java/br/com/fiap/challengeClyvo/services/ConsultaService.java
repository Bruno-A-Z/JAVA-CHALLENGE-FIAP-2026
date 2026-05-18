package br.com.fiap.challengeClyvo.services;

import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import br.com.fiap.challengeClyvo.model.Agendamento;
import br.com.fiap.challengeClyvo.model.Consulta;
import br.com.fiap.challengeClyvo.repository.AgendamentoRepository;
import br.com.fiap.challengeClyvo.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ConsultaService {


    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    // Realiza uma consulta a partir de um agendamento
    public Consulta realizar(Long idAgendamento, Consulta dadosConsulta) {
        Agendamento agendamento = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado."));

        if (agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            throw new IllegalStateException("Não é possível realizar uma consulta cancelada.");
        }

        if (agendamento.getStatus() == StatusAgendamento.REALIZADO) {
            throw new IllegalStateException("Esse agendamento já possui uma consulta realizada.");
        }

        // Vincula a consulta ao agendamento
        dadosConsulta.setAgendamento(agendamento);
        dadosConsulta.setDataRealizacao(LocalDateTime.now());

        // Atualiza o status do agendamento
        agendamento.setStatus(StatusAgendamento.REALIZADO);
        agendamentoRepository.save(agendamento);

        return consultaRepository.save(dadosConsulta);
    }

    // Busca todas as consultas
    public List<Consulta> buscarTodas() {
        return consultaRepository.findAll();
    }

    // Busca consulta por ID
    public Consulta buscarPorId(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada."));
    }

    // Busca histórico médico de um pet
    public List<Consulta> buscarPorPet(Long idPet) {
        return consultaRepository.findByAgendamentoPetId(idPet);
    }

    // Busca consultas realizadas por um veterinário
    public List<Consulta> buscarPorVeterinario(Long idVet) {
        return consultaRepository.findByAgendamentoVeterinarioId(idVet);
    }

    // Atualiza observações de uma consulta
    public Consulta atualizarObservacoes(Long id, String observacoes) {
        Consulta consulta = buscarPorId(id);
        consulta.setObservacoes(observacoes);
        return consultaRepository.save(consulta);
    }

}
