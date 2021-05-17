package com.br.zup.academy.lais.proposta.Carteira;

import com.br.zup.academy.lais.proposta.Cartao.Cartao;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CarteiraRequest {
    public String email;
    public String carteira;

    public CarteiraRequest(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public Carteira toModel(Cartao cartao, TipoCarteira tipoCarteira) {
        return new Carteira(email, carteira, cartao, tipoCarteira);
    }
}
