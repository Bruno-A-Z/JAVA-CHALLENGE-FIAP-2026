package br.com.fiap.challengeClyvo.assemblers;

import br.com.fiap.challengeClyvo.controllers.AgendamentoController;
import br.com.fiap.challengeClyvo.controllers.ConsultaController;
import br.com.fiap.challengeClyvo.controllers.VeterinarioController;
import br.com.fiap.challengeClyvo.dto.response.VeterinarioResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VeterinarioAssembler implements RepresentationModelAssembler<VeterinarioResponseDTO, EntityModel<VeterinarioResponseDTO>> {

    @Override
    public EntityModel<VeterinarioResponseDTO> toModel(VeterinarioResponseDTO veterinario) {
        return EntityModel.of(veterinario,
                linkTo(methodOn(VeterinarioController.class).buscarPorId(veterinario.getId())).withSelfRel(),
                linkTo(methodOn(AgendamentoController.class).buscarPorVeterinario(veterinario.getId(), Pageable.unpaged())).withRel("agendamentos"),
                linkTo(methodOn(ConsultaController.class).buscarPorVeterinario(veterinario.getId(), Pageable.unpaged())).withRel("consultas"),
                linkTo(VeterinarioController.class).withRel("veterinarios"));
    }
}
