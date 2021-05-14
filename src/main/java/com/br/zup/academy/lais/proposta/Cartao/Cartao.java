package com.br.zup.academy.lais.proposta.Cartao;

import com.br.zup.academy.lais.proposta.Proposta.Proposta;

import javax.persistence.*;

@Entity
public class Cartao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @ManyToOne
    public Proposta proposta;
    public String numero;
    public Boolean ativo = true;

    public String getNumero() {
        return numero;
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

    public Cartao(Proposta proposta, String numero) {
        this.proposta = proposta;
        this.numero = numero;
    }

}
