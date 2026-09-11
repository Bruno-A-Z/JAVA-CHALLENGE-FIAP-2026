package br.com.fiap.challengeClyvo.services;

import br.com.fiap.challengeClyvo.dto.request.VeterinarioRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.VeterinarioResponseDTO;
import br.com.fiap.challengeClyvo.entity.Veterinario;
import br.com.fiap.challengeClyvo.enums.UF;
import br.com.fiap.challengeClyvo.exceptions.EntityNotFoundException;
import br.com.fiap.challengeClyvo.mapper.VeterinarioMapper;
import br.com.fiap.challengeClyvo.repository.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarioService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    public VeterinarioResponseDTO salvar(VeterinarioRequestDTO dto) {
        if (veterinarioRepository.findByCrmvNumeroDeInscricao(
                dto.getCrmv().getNumeroDeInscricao()).isPresent()) {
            throw new IllegalStateException("Já existe um veterinário cadastrado com esse CRMV.");
        }
        Veterinario veterinario = VeterinarioMapper.toEntity(dto);
        return VeterinarioMapper.toDTO(veterinarioRepository.save(veterinario));
    }

    public Page<VeterinarioResponseDTO> buscarTodos(Pageable pageable) {
        return veterinarioRepository.findAll(pageable).map(VeterinarioMapper::toDTO);
    }

    public VeterinarioResponseDTO buscarPorId(Long id) {
        return VeterinarioMapper.toDTO(buscarEntidadePorId(id));
    }

    public VeterinarioResponseDTO buscarPorCrmv(int numeroDeInscricao) {
        Veterinario veterinario = veterinarioRepository.findByCrmvNumeroDeInscricao(numeroDeInscricao)
                .orElseThrow(() -> new EntityNotFoundException("Veterinário não encontrado."));
        return VeterinarioMapper.toDTO(veterinario);
    }

    public Page<VeterinarioResponseDTO> buscarPorArea(String area, Pageable pageable) {
        return veterinarioRepository.findByArea(area, pageable).map(VeterinarioMapper::toDTO);
    }

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
