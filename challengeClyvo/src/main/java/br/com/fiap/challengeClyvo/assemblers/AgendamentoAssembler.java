package br.com.fiap.challengeClyvo.assemblers;

import br.com.fiap.challengeClyvo.controllers.AgendamentoController;
import br.com.fiap.challengeClyvo.controllers.ConsultaController;
import br.com.fiap.challengeClyvo.controllers.PetController;
import br.com.fiap.challengeClyvo.controllers.VeterinarioController;
import br.com.fiap.challengeClyvo.dto.response.AgendamentoResponseDTO;
import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AgendamentoAssembler implements RepresentationModelAssembler<AgendamentoResponseDTO, EntityModel<AgendamentoResponseDTO>> {

    @Override
    public EntityModel<AgendamentoResponseDTO> toModel(AgendamentoResponseDTO agendamento) {
        EntityModel<AgendamentoResponseDTO> model = EntityModel.of(agendamento,
                linkTo(methodOn(AgendamentoController.class).buscarPorId(agendamento.getId())).withSelfRel(),
                linkTo(methodOn(PetController.class).buscarPorId(agendamento.getIdPet())).withRel("pet"),
                linkTo(methodOn(VeterinarioController.class).buscarPorId(agendamento.getIdVeterinario())).withRel("veterinario"),
                linkTo(AgendamentoController.class).withRel("agendamentos"));

        // Ações disponíveis dependem do estado: só agendamento AGENDADO pode ser cancelado ou realizado
        if (agendamento.getStatus() == StatusAgendamento.AGENDADO) {
            model.add(
                    linkTo(methodOn(AgendamentoController.class).cancelar(agendamento.getId())).withRel("cancelar"),
                    linkTo(methodOn(ConsultaController.class).realizar(agendamento.getId(), null)).withRel("realizar-consulta"));
        }

        return model;
    }
}
