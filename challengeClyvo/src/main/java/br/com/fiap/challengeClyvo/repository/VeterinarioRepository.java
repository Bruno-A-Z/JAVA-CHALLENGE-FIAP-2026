package br.com.fiap.challengeClyvo.repository;

import br.com.fiap.challengeClyvo.enums.UF;
import br.com.fiap.challengeClyvo.model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {


    List<Veterinario> findByArea(String area);
    List<Veterinario> findByCrmvUf(UF uf);
    List<Veterinario> findByCrmvNumeroInscricao(int numeroDeInscricao);

}
