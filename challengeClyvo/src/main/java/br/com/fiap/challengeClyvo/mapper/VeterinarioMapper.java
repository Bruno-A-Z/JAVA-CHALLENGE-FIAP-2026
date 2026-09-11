package br.com.fiap.challengeClyvo.mapper;

import br.com.fiap.challengeClyvo.dto.CrmvDTO;
import br.com.fiap.challengeClyvo.dto.request.VeterinarioRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.VeterinarioResponseDTO;
import br.com.fiap.challengeClyvo.entity.Crmv;
import br.com.fiap.challengeClyvo.entity.Veterinario;

public class VeterinarioMapper {

    public static Veterinario toEntity(VeterinarioRequestDTO dto) {
        Crmv crmv = new Crmv();
        crmv.setUf(dto.getCrmv().getUf());
        crmv.setNumeroDeInscricao(dto.getCrmv().getNumeroDeInscricao());

        Veterinario veterinario = new Veterinario();
        veterinario.setNome(dto.getNome());
        veterinario.setArea(dto.getArea());
        veterinario.setCrmv(crmv);
        return veterinario;
    }

    public static VeterinarioResponseDTO toDTO(Veterinario veterinario) {
        CrmvDTO crmvDTO = new CrmvDTO(
                veterinario.getCrmv().getUf(),
                veterinario.getCrmv().getNumeroDeInscricao()
        );
        return new VeterinarioResponseDTO(
                veterinario.getId(),
                veterinario.getNome(),
                veterinario.getArea(),
                crmvDTO
        );
    }
}