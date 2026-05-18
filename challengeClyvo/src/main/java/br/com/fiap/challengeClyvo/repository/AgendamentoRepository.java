package br.com.fiap.challengeClyvo.repository;

import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import br.com.fiap.challengeClyvo.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {


    List<Agendamento> findByStatus(StatusAgendamento status);
    List<Agendamento> findByVeterinarioId(Long idVet);
    List<Agendamento> findByPetId(Long idPet);
    List<Agendamento> findByDataHoraBetwen(LocalDateTime inicio, LocalDateTime fim); // Busca por periodo

}
