package br.com.fiap.challengeClyvo.repository;

import br.com.fiap.challengeClyvo.model.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {


    Page<Consulta> findByAgendamentoVeterinarioId(Long idVet, Pageable pageable);
    Page<Consulta> findByAgendamentoPetId(Long idPet, Pageable pageable);

}
