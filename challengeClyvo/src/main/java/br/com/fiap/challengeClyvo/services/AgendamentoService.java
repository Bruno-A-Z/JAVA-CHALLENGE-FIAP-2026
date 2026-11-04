package br.com.fiap.challengeClyvo.services;

import br.com.fiap.challengeClyvo.dto.request.AgendamentoRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.AgendamentoResponseDTO;
import br.com.fiap.challengeClyvo.entity.Agendamento;
import br.com.fiap.challengeClyvo.entity.Pet;
import br.com.fiap.challengeClyvo.entity.Veterinario;
import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import br.com.fiap.challengeClyvo.exceptions.EntityNotFoundException;
import br.com.fiap.challengeClyvo.mapper.AgendamentoMapper;
import br.com.fiap.challengeClyvo.repository.AgendamentoRepository;
import br.com.fiap.challengeClyvo.repository.PetRepository;
import br.com.fiap.challengeClyvo.repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final PetRepository petRepository;
    private final VeterinarioRepository veterinarioRepository;

    public AgendamentoResponseDTO salvar(AgendamentoRequestDTO dto) {
        Pet pet = petRepository.findById(dto.getIdPet())
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado."));

        Veterinario vet = veterinarioRepository.findById(dto.getIdVeterinario())
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado."));

        // Verifica se o vet já tem agendamento ativo (ignora os com status CANCELADO) no mesmo horário
        if (agendamentoRepository.existsByVeterinarioIdAndDataHoraAndStatusNot(
                vet.getId(), dto.getDataHora(), StatusAgendamento.CANCELADO)) {
            throw new IllegalStateException("Veterinário já possui agendamento nesse horário.");
        }

        Agendamento agendamento = AgendamentoMapper.toEntity(dto, pet, vet);
        agendamento.setStatus(StatusAgendamento.AGENDADO);
        return AgendamentoMapper.toDTO(agendamentoRepository.save(agendamento));
    }

    @Transactional(readOnly = true)
    public Page<AgendamentoResponseDTO> buscarTodos(Pageable pageable) {
        return agendamentoRepository.findAll(pageable).map(AgendamentoMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public AgendamentoResponseDTO buscarPorId(Long id) {
        return AgendamentoMapper.toDTO(buscarEntidadePorId(id));
    }

    @Transactional(readOnly = true)
    public Page<AgendamentoResponseDTO> buscarPorStatus(StatusAgendamento status, Pageable pageable) {
        return agendamentoRepository.findByStatus(status, pageable).map(AgendamentoMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<AgendamentoResponseDTO> buscarPorVeterinario(Long id, Pageable pageable) {
        return agendamentoRepository.findByVeterinarioId(id, pageable).map(AgendamentoMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<AgendamentoResponseDTO> buscarPorPet(Long id, Pageable pageable) {
        return agendamentoRepository.findByPetId(id, pageable).map(AgendamentoMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<AgendamentoResponseDTO> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return agendamentoRepository.findByDataHoraBetween(inicio, fim)
                .stream().map(AgendamentoMapper::toDTO).toList();
    }

    public AgendamentoResponseDTO cancelar(Long id) {
        Agendamento agendamento = buscarEntidadePorId(id);

        if (agendamento.getStatus() == StatusAgendamento.REALIZADO) {
            throw new IllegalStateException("Não é possível cancelar uma consulta já realizada.");
        }

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        return AgendamentoMapper.toDTO(agendamentoRepository.save(agendamento));
    }

    // usado pelo ConsultaService, que precisa da entidade completa
    @Transactional(readOnly = true)
    Agendamento buscarEntidadePorId(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado."));
    }
}
