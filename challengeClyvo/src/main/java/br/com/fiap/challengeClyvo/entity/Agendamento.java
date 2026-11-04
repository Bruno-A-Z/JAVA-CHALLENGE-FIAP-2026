package br.com.fiap.challengeClyvo.entity;


import br.com.fiap.challengeClyvo.enums.StatusAgendamento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_AGENDAMENTO")
public class Agendamento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AGENDAMENTO")
    private Long id;

    @NotNull(message = "A data e hora são obrigatórias.")
    @Column(name = "DT_HORA", nullable = false)
    private LocalDateTime dataHora;

    @NotNull(message = "O pet é obrigatório.")
    @ManyToOne
    @JoinColumn(name = "ID_PET", nullable = false)
    @JsonIgnoreProperties(value = {"nome", "especie", "raca", "cor", "idade", "peso", "tutores", "hibernateLazyInitializer", "handler"})
    private Pet pet;

    @NotNull(message = "O veterinário é obrigatório.")
    @ManyToOne
    @JoinColumn(name = "ID_VET", nullable = false)
    @JsonIgnoreProperties(value = {"nome", "area", "crmv", "hibernateLazyInitializer", "handler"})
    private Veterinario veterinario;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private StatusAgendamento status = StatusAgendamento.AGENDADO; // status padrão

    @Column(name = "MOTIVO")
    private String motivo;

    @OneToOne(mappedBy = "agendamento", cascade = CascadeType.ALL)
    @JsonBackReference("consulta-agendamento")
    private Consulta consulta;
}
