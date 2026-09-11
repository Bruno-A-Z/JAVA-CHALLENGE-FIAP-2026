package br.com.fiap.challengeClyvo.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ConsultaRequestDTO {

    @NotBlank(message = "O diagnóstico é obrigatório.")
    private String diagnostico;

    private String tratamento;
    private String observacoes;

    public ConsultaRequestDTO() {
    }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamento() { return tratamento; }
    public void setTratamento(String tratamento) { this.tratamento = tratamento; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}