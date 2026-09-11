package br.com.fiap.challengeClyvo.mapper;

import br.com.fiap.challengeClyvo.dto.request.PetRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.PetResponseDTO;
import br.com.fiap.challengeClyvo.entity.Pet;
import br.com.fiap.challengeClyvo.entity.Tutor;

import java.util.Collections;
import java.util.List;

public class PetMapper {

    public static Pet toEntity(PetRequestDTO dto) {
        Pet pet = new Pet();
        pet.setNome(dto.getNome());
        pet.setEspecie(dto.getEspecie());
        pet.setRaca(dto.getRaca());
        pet.setCor(dto.getCor());
        pet.setIdade(dto.getIdade());
        pet.setPeso(dto.getPeso());
        return pet;
    }

    public static PetResponseDTO toDTO(Pet pet) {
        List<Long> idsTutores = pet.getTutores() == null
                ? Collections.emptyList()
                : pet.getTutores().stream()
                .map(Tutor::getIdTutor)
                .toList();

        return new PetResponseDTO(
                pet.getId(),
                pet.getNome(),
                pet.getEspecie(),
                pet.getRaca(),
                pet.getCor(),
                pet.getIdade(),
                pet.getPeso(),
                idsTutores
        );
    }
}