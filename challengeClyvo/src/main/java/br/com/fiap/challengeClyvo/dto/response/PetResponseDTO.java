package br.com.fiap.challengeClyvo.dto.response;

import java.util.List;

public class PetResponseDTO {

    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private String cor;
    private Integer idade;
    private Double peso;
    private List<Long> idsTutores;

    public PetResponseDTO(Long id, String nome, String especie, String raca, String cor, Integer idade, Double peso, List<Long> idsTutores) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.cor = cor;
        this.idade = idade;
        this.peso = peso;
        this.idsTutores = idsTutores;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEspecie() { return especie; }
    public String getRaca() { return raca; }
    public String getCor() { return cor; }
    public Integer getIdade() { return idade; }
    public Double getPeso() { return peso; }
    public List<Long> getidsTutores() { return idsTutores; }
}