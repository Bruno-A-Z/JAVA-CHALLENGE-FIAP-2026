package br.com.fiap.challengeClyvo.services;

import br.com.fiap.challengeClyvo.dto.request.PetRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.PetResponseDTO;
import br.com.fiap.challengeClyvo.entity.Pet;
import br.com.fiap.challengeClyvo.entity.Tutor;
import br.com.fiap.challengeClyvo.exceptions.EntityNotFoundException;
import br.com.fiap.challengeClyvo.mapper.PetMapper;
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

    public PetResponseDTO salvar(PetRequestDTO dto) {
        Pet pet = PetMapper.toEntity(dto);
        return PetMapper.toDTO(petRepository.save(pet));
    }

    public Page<PetResponseDTO> buscarTodos(Pageable pageable) {
        return petRepository.findAll(pageable).map(PetMapper::toDTO);
    }

    public PetResponseDTO buscarPorId(Long id) {
        return PetMapper.toDTO(buscarEntidadePorId(id));
    }

    public Page<PetResponseDTO> buscarPorNome(String nome, Pageable pageable) {
        return petRepository.findByNome(nome, pageable).map(PetMapper::toDTO);
    }

    public Page<PetResponseDTO> buscarPorEspecie(String especie, Pageable pageable) {
        return petRepository.findByEspecie(especie, pageable).map(PetMapper::toDTO);
    }

    public List<PetResponseDTO> buscarPorTutor(Long id) {
        return petRepository.findByTutoresId(id).stream().map(PetMapper::toDTO).toList();
    }

    public PetResponseDTO adicionarTutor(Long idPet, Long idTutor) {
        Pet pet = buscarEntidadePorId(idPet);
        Tutor tutor = tutorRepository.findById(idTutor)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado."));

        if (pet.getTutores().size() >= LIMITE_TUTORES) {
            throw new IllegalStateException(
                    "Um pet pode ter no máximo " + LIMITE_TUTORES + " tutores."
            );
        }

        pet.getTutores().add(tutor);
        return PetMapper.toDTO(petRepository.save(pet));
    }

    public PetResponseDTO removerTutor(Long idPet, Long idTutor) {
        Pet pet = buscarEntidadePorId(idPet);
        Tutor tutor = tutorRepository.findById(idTutor)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado."));

        if (!pet.getTutores().contains(tutor)) {
            throw new IllegalStateException("Esse tutor não está vinculado a esse pet.");
        }

        pet.getTutores().remove(tutor);
        return PetMapper.toDTO(petRepository.save(pet));
    }

    public PetResponseDTO atualizar(Long id, PetRequestDTO dto) {
        Pet pet = buscarEntidadePorId(id);
        pet.setNome(dto.getNome());
        pet.setEspecie(dto.getEspecie());
        pet.setRaca(dto.getRaca());
        pet.setCor(dto.getCor());
        pet.setIdade(dto.getIdade());
        pet.setPeso(dto.getPeso());
        return PetMapper.toDTO(petRepository.save(pet));
    }

    public void deletar(Long id) {
        buscarEntidadePorId(id);
        petRepository.deleteById(id);
    }

    private Pet buscarEntidadePorId(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado."));
    }
}
