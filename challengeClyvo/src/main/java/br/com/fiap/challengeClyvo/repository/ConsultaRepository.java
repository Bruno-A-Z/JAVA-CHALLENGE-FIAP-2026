package br.com.fiap.challengeClyvo.repository;

import br.com.fiap.challengeClyvo.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ConsultaRepository extends JpaRepository<Consulta, Long> {


    List<Consulta> findByAgendamento(Long idPet);
    List<Consulta> findByAgendamentoVeterinarioId(Long idVet);

}
