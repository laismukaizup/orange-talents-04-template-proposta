package com.br.zup.academy.lais.proposta.Biometria;

import com.br.zup.academy.lais.proposta.SistemaCartao.Cartao;

import javax.persistence.*;

@Entity
public class Biometria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @ManyToOne
    public Cartao cartao;
    public String fingerPrint;

    public Biometria(Cartao cartao, String fingerPrint) {
        this.cartao = cartao;
        this.fingerPrint = fingerPrint;
    }

    @Override
    public String toString() {
        return "Biometria{" +
                "id=" + id +
                ", cartao=" + cartao +
                ", fingerPrint='" + fingerPrint + '\'' +
                '}';
    }
}
