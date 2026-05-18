package br.com.fiap.challengeClyvo.services;


import br.com.fiap.challengeClyvo.enums.UF;
import br.com.fiap.challengeClyvo.model.Veterinario;
import br.com.fiap.challengeClyvo.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VeterinarioService {


    @Autowired
    private VeterinarioRepository veterinarioRepository;


    public Veterinario salvar(Veterinario veterinario) {
        if (veterinarioRepository.findByCrmvNumeroInscricao(
                veterinario.getCrmv().getNumeroDeInscricao()).isPresent()) {
            throw new IllegalStateException("Já existe um veterinário cadastrado com esse CRMV.");
        }
        return veterinarioRepository.save(veterinario);
    }


    public List<Veterinario> buscarTodos() {
        return veterinarioRepository.findAll();
    }


    public Veterinario buscarPorId(Long id) {
        return veterinarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado."));
    }


    public Veterinario buscarPorCrmv(int numeroDeInscricao) {
        return veterinarioRepository.findByCrmvNumeroInscricao(numeroDeInscricao)
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado."));
    }


    public List<Veterinario> buscarPorArea(String area) {
        return veterinarioRepository.findByArea(area);
    }


    public List<Veterinario> buscarPorUf(UF uf) {
        return veterinarioRepository.findByCrmvUf(uf);
    }


    public Veterinario atualizar(Long id, Veterinario vetAtualizado) {
        Veterinario veterinario = buscarPorId(id);
        veterinario.setNome(vetAtualizado.getNome());
        veterinario.setArea(vetAtualizado.getArea());
        return veterinarioRepository.save(veterinario);
    }


    public void deletar(Long id) {
        buscarPorId(id);
        veterinarioRepository.deleteById(id);
    }
}