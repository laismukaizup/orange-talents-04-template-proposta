package com.br.zup.academy.lais.proposta.SistemaCartao;

import com.br.zup.academy.lais.proposta.Proposta.Proposta;

public class CartaoRespose {
    public String id;

    @Deprecated
    public CartaoRespose(){}

    public CartaoRespose(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Cartao toModel(Proposta proposta) {
        return new Cartao(proposta, id);
    }
}
