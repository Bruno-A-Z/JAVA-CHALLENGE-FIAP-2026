package br.com.fiap.challengeClyvo.dto.response;

import java.time.LocalDateTime;

public class ConsultaResponseDTO {

    private Long id;
    private AgendamentoResponseDTO agendamento;
    private LocalDateTime dataRealizacao;
    private String diagnostico;
    private String tratamento;
    private String observacoes;

    public ConsultaResponseDTO(Long id, AgendamentoResponseDTO agendamento, LocalDateTime dataRealizacao, String diagnostico, String tratamento, String observacoes) {
        this.id = id;
        this.agendamento = agendamento;
        this.dataRealizacao = dataRealizacao;
        this.diagnostico = diagnostico;
        this.tratamento = tratamento;
        this.observacoes = observacoes;
    }

    public Long getId() { return id; }
    public AgendamentoResponseDTO getAgendamento() { return agendamento; }
    public LocalDateTime getDataRealizacao() { return dataRealizacao; }
    public String getDiagnostico() { return diagnostico; }
    public String getTratamento() { return tratamento; }
    public String getObservacoes() { return observacoes; }
}