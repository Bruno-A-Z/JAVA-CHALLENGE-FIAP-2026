package br.com.fiap.challengeClyvo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;


@Entity
@Table(name = "TB_TUTOR")
public class Tutor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TUTOR")
    private Long id;

    @NotBlank(message = "O Nome é obrigatório.")
    @Size(min = 3, max = 70, message = "O nome deve ter entre 3 e 70 caracteres.")
    @Column(name = "NOME", nullable = false)
    private String nome;

    @Min(value = 13, message = "Idade inválida")
    @Max(value = 100, message = "Idade Inválida")
    @Column(name = "IDADE")
    private int idade;

    @Column(name = "ENDERECO")
    private String endereco;

    @NotBlank(message = "O numero de telefone é obrigatório")
    @Column(name = "NUM_TELEFONE")
    private String tel;

    @NotBlank(message = "O CPF é obrigatorio.")
    @Size(min = 11, max = 14, message = "O CPF deve ter entre 11 e 14 caracteres.")
    @Column(name = "CPF", unique = true, nullable = false)
    private String cpf;

    @ManyToMany(mappedBy = "tutores")
    @JsonIgnoreProperties("tutores")
    private List<Pet> pets;

    public Tutor(Long id, String nome, int idade, String endereco, String tel, String cpf, List<Pet> pets) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
        this.tel = tel;
        this.cpf = cpf;
        this.pets = pets;
    }

    public Tutor() {
    }


    public Long getIdTutor() {
        return id;
    }
    public void setIdTutor(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

