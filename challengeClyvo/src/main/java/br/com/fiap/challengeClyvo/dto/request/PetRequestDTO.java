package br.com.fiap.challengeClyvo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class PetRequestDTO {

    @NotBlank(message = "O nome é OBRIGATÓRIO.")
    @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
    private String nome;

    @NotBlank(message = "A espécie é obrigatória.")
    private String especie;

    private String raca;
    private String cor;

    @Positive(message = "A idade não pode ser MENOR que Zero.")
    @NotNull(message = "A idade não pode ser nula, ainda que igual a zero.")
    private Integer idade;

    @Positive(message = "O peso deve ser positivo.")
    private Double peso;

    public PetRequestDTO() {
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }
}