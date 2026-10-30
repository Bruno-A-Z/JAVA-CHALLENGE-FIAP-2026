package br.com.fiap.challengeClyvo.assemblers;

import br.com.fiap.challengeClyvo.controllers.AgendamentoController;
import br.com.fiap.challengeClyvo.controllers.ConsultaController;
import br.com.fiap.challengeClyvo.controllers.PetController;
import br.com.fiap.challengeClyvo.controllers.VeterinarioController;
import br.com.fiap.challengeClyvo.dto.response.ConsultaResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ConsultaAssembler implements RepresentationModelAssembler<ConsultaResponseDTO, EntityModel<ConsultaResponseDTO>> {

    @Override
    public EntityModel<ConsultaResponseDTO> toModel(ConsultaResponseDTO consulta) {
        return EntityModel.of(consulta,
                linkTo(methodOn(ConsultaController.class).buscarPorId(consulta.getId())).withSelfRel(),
                linkTo(methodOn(AgendamentoController.class).buscarPorId(consulta.getAgendamento().getId())).withRel("agendamento"),
                linkTo(methodOn(PetController.class).buscarPorId(consulta.getAgendamento().getIdPet())).withRel("pet"),
                linkTo(methodOn(VeterinarioController.class).buscarPorId(consulta.getAgendamento().getIdVeterinario())).withRel("veterinario"),
                linkTo(ConsultaController.class).withRel("consultas"));
    }
}
