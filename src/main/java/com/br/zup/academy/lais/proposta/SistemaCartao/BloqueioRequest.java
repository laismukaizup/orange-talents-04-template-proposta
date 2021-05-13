package com.br.zup.academy.lais.proposta.SistemaCartao;

public class BloqueioRequest {
    public String sistemaResponsavel;

    public BloqueioRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
