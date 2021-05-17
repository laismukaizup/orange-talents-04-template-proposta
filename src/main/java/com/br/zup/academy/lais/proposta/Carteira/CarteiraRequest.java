package com.br.zup.academy.lais.proposta.Carteira;

import com.br.zup.academy.lais.proposta.Cartao.Cartao;
import com.br.zup.academy.lais.proposta.Validacao.UniqueValue;

import javax.validation.constraints.NotBlank;

public class CarteiraRequest {
    @NotBlank
    public String email;
    @UniqueValue(domainClass = Carteira.class, fieldName = "carteira")
    public String carteira;

    public CarteiraRequest(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public Carteira toModel(Cartao cartao) {
        return new Carteira(email, carteira, cartao);
    }
}
