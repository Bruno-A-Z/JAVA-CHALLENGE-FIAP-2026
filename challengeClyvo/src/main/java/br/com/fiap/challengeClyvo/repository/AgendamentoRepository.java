package br.com.fiap.challengeClyvo.repository;

import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import br.com.fiap.challengeClyvo.model.Agendamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {


    Page<Agendamento> findByStatus(StatusAgendamento status, Pageable pageable);
    Page<Agendamento> findByVeterinarioId(Long id, Pageable pageable);
    Page<Agendamento> findByPetId(Long id, Pageable pageable);
    List<Agendamento> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Agendamento> findByVeterinarioIdAndDataHora(long id, LocalDateTime dataHora);
}
