package br.com.fiap.challengeClyvo.services;

import br.com.fiap.challengeClyvo.dto.request.ConsultaRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.ConsultaResponseDTO;
import br.com.fiap.challengeClyvo.entity.Agendamento;
import br.com.fiap.challengeClyvo.entity.Consulta;
import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import br.com.fiap.challengeClyvo.exceptions.EntityNotFoundException;
import br.com.fiap.challengeClyvo.mapper.ConsultaMapper;
import br.com.fiap.challengeClyvo.repository.AgendamentoRepository;
import br.com.fiap.challengeClyvo.repository.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final AgendamentoRepository agendamentoRepository;

    // Realiza uma consulta a partir de um agendamento
    @Transactional
    public ConsultaResponseDTO realizar(Long id, ConsultaRequestDTO dto) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado."));

        if (agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            throw new IllegalStateException("Não é possível realizar uma consulta cancelada.");
        }

        if (agendamento.getStatus() == StatusAgendamento.REALIZADO) {
            throw new IllegalStateException("Esse agendamento já possui uma consulta realizada.");
        }

        Consulta consulta = ConsultaMapper.toEntity(dto);
        consulta.setAgendamento(agendamento);
        consulta.setDataRealizacao(LocalDateTime.now());

        agendamento.setStatus(StatusAgendamento.REALIZADO);
        agendamentoRepository.save(agendamento);

        return ConsultaMapper.toDTO(consultaRepository.save(consulta));
    }

    @Transactional(readOnly = true)
    public Page<ConsultaResponseDTO> buscarTodas(Pageable pageable) {
        return consultaRepository.findAll(pageable).map(ConsultaMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public ConsultaResponseDTO buscarPorId(Long id) {
        return ConsultaMapper.toDTO(buscarEntidadePorId(id));
    }

    @Transactional(readOnly = true)
    public Page<ConsultaResponseDTO> buscarPorPet(Long id, Pageable pageable) {
        return consultaRepository.findByAgendamentoPetId(id, pageable).map(ConsultaMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<ConsultaResponseDTO> buscarPorVeterinario(Long id, Pageable pageable) {
        return consultaRepository.findByAgendamentoVeterinarioId(id, pageable).map(ConsultaMapper::toDTO);
    }

    public ConsultaResponseDTO atualizarObservacoes(Long id, String observacoes) {
        Consulta consulta = buscarEntidadePorId(id);
        consulta.setObservacoes(observacoes);
        return ConsultaMapper.toDTO(consultaRepository.save(consulta));
    }

    private Consulta buscarEntidadePorId(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada."));
    }
}
