package br.com.fiap.challengeClyvo.services;

import br.com.fiap.challengeClyvo.dto.request.TutorRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.TutorResponseDTO;
import br.com.fiap.challengeClyvo.entity.Tutor;
import br.com.fiap.challengeClyvo.exceptions.EntityNotFoundException;
import br.com.fiap.challengeClyvo.mapper.TutorMapper;
import br.com.fiap.challengeClyvo.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    public TutorResponseDTO salvar(TutorRequestDTO dto) {
        if (tutorRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new IllegalStateException("Já existe um tutor cadastrado com esse CPF.");
        }
        Tutor tutor = TutorMapper.toEntity(dto);
        return TutorMapper.toDTO(tutorRepository.save(tutor));
    }

    public Page<TutorResponseDTO> buscarTodos(Pageable pageable) {
        return tutorRepository.findAll(pageable).map(TutorMapper::toDTO);
    }

    public TutorResponseDTO buscarPorId(Long id) {
        return TutorMapper.toDTO(buscarEntidadePorId(id));
    }

    public TutorResponseDTO buscarPorCpf(String cpf) {
        Tutor tutor = tutorRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado."));
        return TutorMapper.toDTO(tutor);
    }

    public Page<TutorResponseDTO> buscarPorNome(String nome, Pageable pageable) {
        return tutorRepository.findByNome(nome, pageable).map(TutorMapper::toDTO);
    }

    public TutorResponseDTO atualizar(Long id, TutorRequestDTO dto) {
        Tutor tutor = buscarEntidadePorId(id);
        tutor.setNome(dto.getNome());
        tutor.setIdade(dto.getIdade());
        tutor.setEndereco(dto.getEndereco());
        tutor.setTel(dto.getTel());
        return TutorMapper.toDTO(tutorRepository.save(tutor));
    }

    public void deletar(Long id) {
        buscarEntidadePorId(id);
        tutorRepository.deleteById(id);
    }

    private Tutor buscarEntidadePorId(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado."));
    }
}
