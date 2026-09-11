package br.com.fiap.challengeClyvo.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TutorRequestDTO {

    @NotBlank(message = "O Nome é obrigatório.")
    @Size(min = 3, max = 70, message = "O nome deve ter entre 3 e 70 caracteres.")
    private String nome;

    @Min(value = 13, message = "Idade inválida")
    @Max(value = 100, message = "Idade Inválida")
    private int idade;

    private String endereco;

    @NotBlank(message = "O numero de telefone é obrigatório")
    private String tel;

    @NotBlank(message = "O CPF é obrigatorio.")
    @Size(min = 11, max = 14, message = "O CPF deve ter entre 11 e 14 caracteres.")
    private String cpf;

    public TutorRequestDTO() {
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}