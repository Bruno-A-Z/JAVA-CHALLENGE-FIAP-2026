package br.com.fiap.challengeClyvo.mapper;

import br.com.fiap.challengeClyvo.dto.request.ConsultaRequestDTO;
import br.com.fiap.challengeClyvo.dto.response.ConsultaResponseDTO;
import br.com.fiap.challengeClyvo.entity.Consulta;

public class ConsultaMapper {

    public static Consulta toEntity(ConsultaRequestDTO dto) {
        Consulta consulta = new Consulta();
        consulta.setDiagnostico(dto.getDiagnostico());
        consulta.setTratamento(dto.getTratamento());
        consulta.setObservacoes(dto.getObservacoes());
        return consulta;
    }

    public static ConsultaResponseDTO toDTO(Consulta consulta) {
        return new ConsultaResponseDTO(
                consulta.getId(),
                AgendamentoMapper.toDTO(consulta.getAgendamento()),
                consulta.getDataRealizacao(),
                consulta.getDiagnostico(),
                consulta.getTratamento(),
                consulta.getObservacoes()
        );
    }
}