package com.br.zup.academy.lais.proposta.SistemFinanceiro;

import com.br.zup.academy.lais.proposta.Proposta.Proposta;

public class AvaliacaoRequest {
    public String documento;
    public String nome;
    public String idProposta;

    public AvaliacaoRequest(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public AvaliacaoRequest(Proposta p){
        this.documento = p.getDocumento();
        this.nome = p.getNome();
        this.idProposta = p.getId().toString();
    }
}
