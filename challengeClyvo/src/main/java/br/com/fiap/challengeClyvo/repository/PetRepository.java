package br.com.fiap.challengeClyvo.repository;

import br.com.fiap.challengeClyvo.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {


    Page<Pet> findByNome(String nome, Pageable pageable);
    Page<Pet> findByEspecie(String especie, Pageable pageable); //Para caso exista outro pet com o mesmo nome
    List<Pet> findByTutores(Long id); //Listar todos os pets do tutor

}
