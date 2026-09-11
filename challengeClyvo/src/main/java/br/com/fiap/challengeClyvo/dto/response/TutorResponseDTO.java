package br.com.fiap.challengeClyvo.dto.response;

import java.util.List;

public class TutorResponseDTO {

    private Long id;
    private String nome;
    private int idade;
    private String endereco;
    private String tel;
    private String cpf;
    private List<Long> idsPets;

    public TutorResponseDTO(Long id, String nome, int idade, String endereco, String tel, String cpf, List<Long> idsPets) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
        this.tel = tel;
        this.cpf = cpf;
        this.idsPets = idsPets;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getEndereco() { return endereco; }
    public String getTel() { return tel; }
    public String getCpf() { return cpf; }
    public List<Long> getidsPets() { return idsPets; }
}