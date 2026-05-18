package br.com.fiap.challengeClyvo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "TB_PET")
public class Pet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "pet_seq")
    @SequenceGenerator()
    @Column(name = "ID_PET")
    private long idPet;

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
    private int idade;

    @Positive(message = "O peso deve ser positivo.")
    @Column(name = "PESO")
    private double peso;

    //Relacionamento do pet por tutor
    @ManyToOne
    @JoinColumn(name = "ID_TUTOR", nullable = false)
    private Tutor tutor;

    public Pet(long idPet, String nome, String especie, String raca, String cor, int idade, double peso, Tutor tutor) {
        this.idPet = idPet;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.cor = cor;
        this.idade = idade;
        this.peso = peso;
        this.tutor = tutor;
    }

    public Pet() {
    }

    public long getIdPet() {
        return idPet;
    }
    public void setIdPet(long idPet) {
        this.idPet = idPet;
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

    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Tutor getTutor() {
        return tutor;
    }
    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
}