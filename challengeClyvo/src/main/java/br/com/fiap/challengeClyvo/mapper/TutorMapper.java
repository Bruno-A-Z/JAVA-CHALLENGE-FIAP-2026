package br.com.fiap.challengeClyvo.mapper;

import br.com.fiap.challengeClyvo.dto.request.TutorRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.TutorResponseDTO;
import br.com.fiap.challengeClyvo.entity.Pet;
import br.com.fiap.challengeClyvo.entity.Tutor;

import java.util.Collections;
import java.util.List;

public class TutorMapper {

    public static Tutor toEntity(TutorRequestDTO dto) {
        Tutor tutor = new Tutor();
        tutor.setNome(dto.getNome());
        tutor.setIdade(dto.getIdade());
        tutor.setEndereco(dto.getEndereco());
        tutor.setTel(dto.getTel());
        tutor.setCpf(dto.getCpf());
        return tutor;
    }

    public static TutorResponseDTO toDTO(Tutor tutor) {
        List<Long> idsPets = tutor.getPets() == null
                ? Collections.emptyList()
                : tutor.getPets().stream()
                .map(Pet::getId)
                .toList();

        return new TutorResponseDTO(
                tutor.getIdTutor(),
                tutor.getNome(),
                tutor.getIdade(),
                tutor.getEndereco(),
                tutor.getTel(),
                tutor.getCpf(),
                idsPets
        );
    }
}