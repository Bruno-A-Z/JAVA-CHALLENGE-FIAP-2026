package br.com.fiap.challengeClyvo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;


@Entity
@Table(name = "TB_PET")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class Pet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pet_seq")
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

    @Positive(message = "A idade não pode ser MENOR que Zero.")
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

    public Pet(Long id, String nome, String especie, String raca, String cor, Integer idade, Double peso, List<Tutor> tutores) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.cor = cor;
        this.idade = idade;
        this.peso = peso;
        this.tutores = tutores;
    }

    public Pet() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }
    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }
    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getCor() {
        return cor;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }

    public Integer getIdade() {
        return idade;
    }
    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Double getPeso() {
        return peso;
    }
    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public List<Tutor> getTutores() {
        return tutores;
    }
    public void setTutores(List<Tutor> tutores) {
        this.tutores = tutores;
    }
}