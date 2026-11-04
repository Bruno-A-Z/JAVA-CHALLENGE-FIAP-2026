package br.com.fiap.challengeClyvo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "TB_CONSULTA")
public class Consulta {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONSULTA")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_AGENDAMENTO")
    @JsonManagedReference("consulta-agendamento")
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
}
