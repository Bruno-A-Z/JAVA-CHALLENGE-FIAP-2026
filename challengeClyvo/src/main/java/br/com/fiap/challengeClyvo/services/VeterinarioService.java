package br.com.fiap.challengeClyvo.services;

import br.com.fiap.challengeClyvo.dto.request.VeterinarioRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.VeterinarioResponseDTO;
import br.com.fiap.challengeClyvo.entity.Veterinario;
import br.com.fiap.challengeClyvo.enums.UF;
import br.com.fiap.challengeClyvo.exceptions.EntityNotFoundException;
import br.com.fiap.challengeClyvo.mapper.VeterinarioMapper;
import br.com.fiap.challengeClyvo.repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioResponseDTO salvar(VeterinarioRequestDTO dto) {
        if (veterinarioRepository.findByCrmvNumeroDeInscricao(
                dto.getCrmv().getNumeroDeInscricao()).isPresent()) {
            throw new IllegalStateException("Já existe um veterinário cadastrado com esse CRMV.");
        }
        Veterinario veterinario = VeterinarioMapper.toEntity(dto);
        return VeterinarioMapper.toDTO(veterinarioRepository.save(veterinario));
    }

    @Transactional(readOnly = true)
    public Page<VeterinarioResponseDTO> buscarTodos(Pageable pageable) {
        return veterinarioRepository.findAll(pageable).map(VeterinarioMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public VeterinarioResponseDTO buscarPorId(Long id) {
        return VeterinarioMapper.toDTO(buscarEntidadePorId(id));
    }

    @Transactional(readOnly = true)
    public VeterinarioResponseDTO buscarPorCrmv(int numeroDeInscricao) {
        Veterinario veterinario = veterinarioRepository.findByCrmvNumeroDeInscricao(numeroDeInscricao)
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado."));
        return VeterinarioMapper.toDTO(veterinario);
    }

    @Transactional(readOnly = true)
    public Page<VeterinarioResponseDTO> buscarPorArea(String area, Pageable pageable) {
        return veterinarioRepository.findByArea(area, pageable).map(VeterinarioMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<VeterinarioResponseDTO> buscarPorUf(UF uf) {
        return veterinarioRepository.findByCrmvUf(uf).stream().map(VeterinarioMapper::toDTO).toList();
    }

    public VeterinarioResponseDTO atualizar(Long id, VeterinarioRequestDTO dto) {
        Veterinario veterinario = buscarEntidadePorId(id);
        veterinario.setNome(dto.getNome());
        veterinario.setArea(dto.getArea());
        return VeterinarioMapper.toDTO(veterinarioRepository.save(veterinario));
    }

    public void deletar(Long id) {
        buscarEntidadePorId(id);
        veterinarioRepository.deleteById(id);
    }

    private Veterinario buscarEntidadePorId(Long id) {
        return veterinarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado."));
    }
}
