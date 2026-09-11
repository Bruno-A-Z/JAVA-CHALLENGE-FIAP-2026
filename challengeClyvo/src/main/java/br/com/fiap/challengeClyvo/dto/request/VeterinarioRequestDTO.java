package br.com.fiap.challengeClyvo.dto.request;

import br.com.fiap.challengeClyvo.dto.CrmvDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class VeterinarioRequestDTO {

    @NotBlank(message = "O Nome é obrigatorio")
    @Size(min = 3, max = 70, message = "O nome deve ter entre 3 e 70 caracteres")
    private String nome;

    @NotBlank(message = "A área de atuação e obrigatória")
    @Size(min = 3, max = 40)
    private String area;

    @NotNull(message = "O CRMV é obrigatório")
    @Valid
    private CrmvDTO crmv;

    public VeterinarioRequestDTO() {
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public CrmvDTO getCrmv() { return crmv; }
    public void setCrmv(CrmvDTO crmv) { this.crmv = crmv; }
}