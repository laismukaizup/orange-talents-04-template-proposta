package com.br.zup.academy.lais.proposta.SistemaCartao;

import com.br.zup.academy.lais.proposta.Proposta.Proposta;

import javax.persistence.*;

@Entity
public class Cartao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @ManyToOne
    public Proposta proposta;
    public String numero;

    public Cartao(Proposta proposta, String numero) {
        this.proposta = proposta;
        this.numero = numero;
    }
}
