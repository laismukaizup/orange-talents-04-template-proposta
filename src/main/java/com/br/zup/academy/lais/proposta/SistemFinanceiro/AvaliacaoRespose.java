package com.br.zup.academy.lais.proposta.SistemFinanceiro;

public class AvaliacaoRespose {
    public String documento;
    public String nome;
    public String idProposta;
    public StatusAnalise resultadoSolicitacao;

    public AvaliacaoRespose(String documento, String nome, String idProposta, StatusAnalise resultadoSolicitacao) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public StatusAnalise getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }
}
