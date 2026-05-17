package br.com.fiap.challengeClyvo.model;


import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_AGENDAMENTO")
public class Agendamento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AGENDAMENTO")
    private Long id;

    @NotNull(message = "A data e hora são obrigatórias.")
    @Future(message = "O agendamento deve ser em uma data futura.")
    @Column(name = "DT_HORA", nullable = false)
    private LocalDateTime dataHora;

    @NotNull(message = "O pet é obrigatório.")
    @ManyToOne
    @JoinColumn(name = "ID_PET", nullable = false)
    private Pet pet;

    @NotNull(message = "O veterinário é obrigatório.")
    @ManyToOne
    @JoinColumn(name = "ID_VET", nullable = false)
    private Veterinario veterinario;

    @NotNull(message = "O status é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private StatusAgendamento status = StatusAgendamento.AGENDADO; // status padrão

    @Column(name = "MOTIVO")
    private String motivo;

    @OneToOne(mappedBy = "agendamento", cascade = CascadeType.ALL)
    private Consulta consulta;


    public Agendamento(Long id, LocalDateTime dataHora, Pet pet, Veterinario veterinario, StatusAgendamento status, String motivo, Consulta consulta) {
        this.id = id;
        this.dataHora = dataHora;
        this.pet = pet;
        this.veterinario = veterinario;
        this.status = status;
        this.motivo = motivo;
        this.consulta = consulta;
    }

    public Agendamento() {
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Pet getPet() {
        return pet;
    }
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }
    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public StatusAgendamento getStatus() {
        return status;
    }
    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Consulta getConsulta() {
        return consulta;
    }
    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

}
