package com.br.zup.academy.lais.proposta.Carteira;

import com.br.zup.academy.lais.proposta.Cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Carteira {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotBlank
    public String email;
    @NotBlank
    public String carteira;
    @ManyToOne
    public Cartao cartao;
    public TipoCarteira tipoCarteira = TipoCarteira.EMPTY;

    public Carteira(String email, String carteira, Cartao cartao, TipoCarteira tipoCarteira) {
        this.email = email;
        this.carteira = carteira;
        this.cartao = cartao;
        this.tipoCarteira = tipoCarteira;
    }


}
