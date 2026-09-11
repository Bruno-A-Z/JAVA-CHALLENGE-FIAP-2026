package br.com.fiap.challengeClyvo.dto.response;

import br.com.fiap.challengeClyvo.dto.CrmvDTO;

public class VeterinarioResponseDTO {

    private Long id;
    private String nome;
    private String area;
    private CrmvDTO crmv;

    public VeterinarioResponseDTO(Long id, String nome, String area, CrmvDTO crmv) {
        this.id = id;
        this.nome = nome;
        this.area = area;
        this.crmv = crmv;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getArea() { return area; }
    public CrmvDTO getCrmv() { return crmv; }
}