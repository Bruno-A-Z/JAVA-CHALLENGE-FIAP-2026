package br.com.fiap.challengeClyvo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_PET")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class Pet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PET")
    private Long id;

    @NotBlank(message = "O nome é OBRIGATÓRIO.")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    @Column(name = "NOME")
    private String nome;

    @NotBlank(message = "A espécie é obrigatória.")
    @Column(name = "ESPECIE", nullable = false)
    private String especie;

    @Column(name = "RACA")
    private String raca;

    @Column(name = "COR")
    private String cor;

    @PositiveOrZero(message = "A idade não pode ser MENOR que Zero.")
    @NotNull(message = "A idade não pode ser nula, ainda que igual a zero.")
    @Column(name = "IDADE")
    private Integer idade;

    @Positive(message = "O peso deve ser positivo.")
    @Column(name = "PESO")
    private Double peso;

    //Relacionamento do pet por tutor
    @ManyToMany
    @JoinTable(name = "TB_PET_TUTOR",
            joinColumns = @JoinColumn(name = "ID_PET"),
            inverseJoinColumns = @JoinColumn(name = "ID_TUTOR")
    )
    @JsonIgnoreProperties("pets")
    private List<Tutor> tutores;
}