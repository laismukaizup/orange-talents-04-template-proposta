package com.br.zup.academy.lais.proposta.SistemaCartao;

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
}
