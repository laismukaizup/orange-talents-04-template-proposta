package com.br.zup.academy.lais.proposta.Proposta;

public class PropostaDetalheResponse {
    public String estado;

    @Deprecated
    public PropostaDetalheResponse(){}

    public PropostaDetalheResponse(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
