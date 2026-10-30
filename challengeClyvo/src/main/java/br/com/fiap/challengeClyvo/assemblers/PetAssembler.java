package br.com.fiap.challengeClyvo.assemblers;

import br.com.fiap.challengeClyvo.controllers.AgendamentoController;
import br.com.fiap.challengeClyvo.controllers.ConsultaController;
import br.com.fiap.challengeClyvo.controllers.PetController;
import br.com.fiap.challengeClyvo.dto.response.PetResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PetAssembler implements RepresentationModelAssembler<PetResponseDTO, EntityModel<PetResponseDTO>> {

    @Override
    public EntityModel<PetResponseDTO> toModel(PetResponseDTO pet) {
        return EntityModel.of(pet,
                linkTo(methodOn(PetController.class).buscarPorId(pet.getId())).withSelfRel(),
                linkTo(methodOn(AgendamentoController.class).buscarPorPet(pet.getId(), Pageable.unpaged())).withRel("agendamentos"),
                linkTo(methodOn(ConsultaController.class).buscarPorPet(pet.getId(), Pageable.unpaged())).withRel("consultas"),
                linkTo(PetController.class).withRel("pets"));
    }
}
