package br.com.fiap.challengeClyvo.repository;

import br.com.fiap.challengeClyvo.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PetRepository extends JpaRepository<Pet, Long> {


    List<Pet> findByNome(String nome);
    List<Pet> findByEspecie(String especie); //Para caso exista outro pet com o mesmo nome
    List<Pet> findByTutores(Long idTutor); //Listar todos os pets do tutor

}
