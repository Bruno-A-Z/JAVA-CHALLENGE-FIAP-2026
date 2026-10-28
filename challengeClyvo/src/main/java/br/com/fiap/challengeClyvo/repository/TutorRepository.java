package br.com.fiap.challengeClyvo.repository;

import br.com.fiap.challengeClyvo.entity.Tutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    @Query("select t from Tutor t where replace(replace(t.cpf, '.', ''), '-', '') = :cpf")//"Regex" basico para diminuir possiveis erros/dificuldades de usuario
    Optional<Tutor> findByCpf(@Param("cpf") String cpf);
    Page<Tutor> findByNome(String nome, Pageable pageable);
    List<Tutor> findByPetsId(Long idPet); // Listar todos os tutores de um pet

}
