package com.br.zup.academy.lais.proposta.Cartao;

import com.br.zup.academy.lais.proposta.Carteira.TipoCarteira;
import com.br.zup.academy.lais.proposta.Proposta.Proposta;

import javax.persistence.*;

@Entity
public class Cartao {
    @Id
    public String id;
    @ManyToOne
    public Proposta proposta;
    public Boolean ativo = true;
    public TipoCarteira tipoCarteira = TipoCarteira.EMPTY;

    public void setTipoCarteira(TipoCarteira tipoCarteira) {
        this.tipoCarteira = tipoCarteira;
    }

    public String getId() {
        return id;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }


    @Deprecated
    public Cartao() {
    }

    public Cartao(Proposta proposta, String id) {
        this.proposta = proposta;
        this.id = id;
    }

}
