package br.com.fiap.challengeClyvo.repository;

import br.com.fiap.challengeClyvo.model.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {


    Optional<Tutor> findByCpf(String cpf);
    Page<Tutor> findByNome(String nome, Pageable pageable);

}
