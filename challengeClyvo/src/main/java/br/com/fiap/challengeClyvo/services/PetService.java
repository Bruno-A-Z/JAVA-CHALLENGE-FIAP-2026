package br.com.fiap.challengeClyvo.services;


import br.com.fiap.challengeClyvo.exceptions.EntityNotFoundException;
import br.com.fiap.challengeClyvo.model.Pet;
import br.com.fiap.challengeClyvo.model.Tutor;
import br.com.fiap.challengeClyvo.repository.PetRepository;
import br.com.fiap.challengeClyvo.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PetService {


    private static final int LIMITE_TUTORES = 2;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private TutorRepository tutorRepository;

    public Pet salvar(Pet pet) {
        return petRepository.save(pet);
    }

    public Page<Pet> buscarTodos(Pageable pageable) {
        return petRepository.findAll(pageable);
    }

    public Pet buscarPorId(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado."));
    }

    public Page<Pet> buscarPorNome(String nome, Pageable pageable) {
        return petRepository.findByNome(nome, pageable);
    }

    public Page<Pet> buscarPorEspecie(String especie, Pageable pageable) {
        return petRepository.findByEspecie(especie, pageable);
    }

    public List<Pet> buscarPorTutor(Long idTutores) {
        return petRepository.findByTutores(idTutores);
    }

    public Pet adicionarTutor(Long idPet, Long idTutor) {
        Pet pet = buscarPorId(idPet);
        Tutor tutor = tutorRepository.findById(idTutor)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado."));

        if (pet.getTutores().size() >= LIMITE_TUTORES) {
            throw new IllegalStateException(
                    "Um pet pode ter no máximo " + LIMITE_TUTORES + " tutores."
            );
        }

        pet.getTutores().add(tutor);
        return petRepository.save(pet);
    }

    public Pet atualizar(Long id, Pet petAtualizado) {
        Pet pet = buscarPorId(id);
        pet.setNome(petAtualizado.getNome());
        pet.setEspecie(petAtualizado.getEspecie());
        pet.setRaca(petAtualizado.getRaca());
        pet.setCor(petAtualizado.getCor());
        pet.setIdade(petAtualizado.getIdade());
        pet.setPeso(petAtualizado.getPeso());
        return petRepository.save(pet);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        petRepository.deleteById(id);
    }


}
