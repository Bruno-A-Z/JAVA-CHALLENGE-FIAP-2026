package br.com.fiap.challengeClyvo.model;

import br.com.fiap.challengeClyvo.enums.UF;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@Embeddable
public class Crmv {


    @Column(name = "CRMV_PREFIXO")
    private String prefixo = "CRMV";

    @NotNull(message = "A UF é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(name = "CRMV_UF")
    private UF uf;

    @Positive(message = "Não digite números negativos")
    @NotNull(message = "O numero de inscrição é obrigatório")
    @Column(name = "CRMV_NUMINSCRICAO")
    private int numeroDeInscricao;

    @NotNull(message = "O sufixo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "CRMV_SUFIXO")
    private SufixoCrmv sufixo;

    private enum SufixoCrmv{
     /*Medico veterinário/ Zootecnista */   MV, ZT
    }

    public Crmv(String prefixo, UF uf, int numeroDeInscricao, SufixoCrmv sufixo) {
        this.prefixo = prefixo;
        this.uf = uf;
        this.numeroDeInscricao = numeroDeInscricao;
        this.sufixo = sufixo;
    }

    public Crmv() {
    }


    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
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

    public SufixoCrmv getSufixo() {
        return sufixo;
    }

    public void setSufixo(SufixoCrmv sufixo) {
        this.sufixo = sufixo;
    }
}
