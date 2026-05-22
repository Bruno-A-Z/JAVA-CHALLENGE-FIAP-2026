package br.com.fiap.challengeClyvo.repository;

import br.com.fiap.challengeClyvo.enums.UF;
import br.com.fiap.challengeClyvo.model.Veterinario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {


    Page<Veterinario> findByArea(String area, Pageable pageable);
    List<Veterinario> findByCrmvUf(UF uf);
    Optional<Veterinario> findByCrmvNumeroDeInscricao(int numeroDeInscricao);

}
