package br.com.fiap.challengeClyvo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "TB_VET")
public class Veterinario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "vet_seq")
    @Column(name = "ID_VET")
    private long id;

    @NotBlank(message = "O Nome é obrigatorio")
    @Size(min = 3, max = 70, message = "O nome deve ter entre 3 e 70 caracteres")
    @Column(name = "NOME")
    private String nome;

    @NotBlank(message = "A área de atuação e obrigatória")
    @Size(min = 3, max = 40)
    @Column(name = "AREA")
    private String area;

    // Inicio da Identidade/CRMV | Procurando por maneira de deixar com uma formatacao melhor
    @NotBlank(message = "O CRMV é obrigatório no seguinte formato: CRMV-SP 12345/MV")
    @Column(name = "CRMV", unique = true, nullable = false)
    @Embedded
    private Crmv crmv;

    public Veterinario(long id, String nome, String area, Crmv crmv) {
        this.id = id;
        this.nome = nome;
        this.area = area;
        this.crmv = crmv;
    }

    public Veterinario() {
    }

    public long getId() {
        return id;
    }
    public void setId(long idVet) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    public Crmv getCrmv() {
        return crmv;
    }
    public void setCrmv(Crmv crmv) {
        this.crmv = crmv;
    }
}
