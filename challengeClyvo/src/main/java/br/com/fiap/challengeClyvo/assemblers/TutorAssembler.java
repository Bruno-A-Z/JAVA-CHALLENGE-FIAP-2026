package br.com.fiap.challengeClyvo.assemblers;

import br.com.fiap.challengeClyvo.controllers.PetController;
import br.com.fiap.challengeClyvo.controllers.TutorController;
import br.com.fiap.challengeClyvo.dto.response.TutorResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TutorAssembler implements RepresentationModelAssembler<TutorResponseDTO, EntityModel<TutorResponseDTO>> {

    @Override
    public EntityModel<TutorResponseDTO> toModel(TutorResponseDTO tutor) {
        return EntityModel.of(tutor,
                linkTo(methodOn(TutorController.class).buscarPorId(tutor.getId())).withSelfRel(),
                linkTo(methodOn(PetController.class).buscarPorTutor(tutor.getId())).withRel("pets"),
                linkTo(TutorController.class).withRel("tutores"));
    }
}
