package br.com.fiap.challengeClyvo.repository;

import br.com.fiap.challengeClyvo.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TutorRepository extends JpaRepository<Tutor, Long> {


    List<Tutor> findByCpf(String cpf);
    List<Tutor> findByNome(String nome);

}
