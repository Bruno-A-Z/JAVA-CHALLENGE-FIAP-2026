package br.com.fiap.challengeClyvo.dto;

import br.com.fiap.challengeClyvo.enums.UF;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CrmvDTO {

    @NotNull(message = "A UF é obrigatória")
    private UF uf;

    @Positive(message = "Não digite números negativos")
    @NotNull(message = "O numero de inscrição é obrigatório")
    private int numeroDeInscricao;

    public CrmvDTO() {
    }

    public CrmvDTO(UF uf, int numeroDeInscricao) {
        this.uf = uf;
        this.numeroDeInscricao = numeroDeInscricao;
    }

    public UF getUf() {
        return uf;
    }
    public void setUf(UF uf) {
        this.uf = uf;
    }

    public int getNumeroDeInscricao() {
        return numeroDeInscricao;
    }
    public void setNumeroDeInscricao(int numeroDeInscricao) {
        this.numeroDeInscricao = numeroDeInscricao;
    }
}