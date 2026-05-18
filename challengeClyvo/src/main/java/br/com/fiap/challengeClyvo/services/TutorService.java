package br.com.fiap.challengeClyvo.services;


import br.com.fiap.challengeClyvo.model.Tutor;
import br.com.fiap.challengeClyvo.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {


    @Autowired
    private TutorRepository tutorRepository;

    public Tutor salvar(Tutor tutor) {
        if (tutorRepository.findByCpf(tutor.getCpf()).isPresent()) {
            throw new IllegalStateException("Já existe um tutor cadastrado com esse CPF.");
        }
        return tutorRepository.save(tutor);
    }

    public List<Tutor> buscarTodos() {
        return tutorRepository.findAll();
    }

    public Tutor buscarPorId(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado."));
    }

    public Tutor buscarPorCpf(String cpf) {
        return tutorRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado."));
    }

    public List<Tutor> buscarPorNome(String nome) {
        return tutorRepository.findByNome(nome);
    }

    public Tutor atualizar(Long id, Tutor tutorAtualizado) {
        Tutor tutor = buscarPorId(id);
        tutor.setNome(tutorAtualizado.getNome());
        tutor.setIdade(tutorAtualizado.getIdade());
        tutor.setEndereco(tutorAtualizado.getEndereco());
        tutor.setTel(tutorAtualizado.getTel());
        return tutorRepository.save(tutor);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        tutorRepository.deleteById(id);
    }

}
