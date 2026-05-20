package br.com.fiap.challengeClyvo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;


@Entity
@Table(name = "TB_CONSULTA")
public class Consulta {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONSULTA")
    private Long idConsulta;

    @Column
    @OneToOne
    @JoinColumn(name = "ID_AGENDAMENTO")
    private Agendamento agendamento;

    @Column(name = "DT_REALIZACAO", nullable = false)
    private LocalDateTime dataRealizacao;

    @NotBlank(message = "O diagnóstico é obrigatório.")
    @Column(name = "DIAGNOSTICO", nullable = false, length = 500)
    private String diagnostico;

    @Column(name = "TRATAMENTO", length = 500)
    private String tratamento;

    @Column(name = "OBSERVACOES", length = 500)
    private String observacoes;

    public Consulta(Long idConsulta, Agendamento agendamento, LocalDateTime dataRealizacao, String diagnostico, String tratamento, String observacoes) {
        this.idConsulta = idConsulta;
        this.agendamento = agendamento;
        this.dataRealizacao = dataRealizacao;
        this.diagnostico = diagnostico;
        this.tratamento = tratamento;
        this.observacoes = observacoes;
    }

    public Consulta() {
    }

    public Long getIdConsulta() {
        return idConsulta;
    }
    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }
    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public LocalDateTime getDataRealizacao() {
        return dataRealizacao;
    }
    public void setDataRealizacao(LocalDateTime dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public String getDiagnostico() {
        return diagnostico;
    }
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamento() {
        return tratamento;
    }
    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }

    public String getObservacoes() {
        return observacoes;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
