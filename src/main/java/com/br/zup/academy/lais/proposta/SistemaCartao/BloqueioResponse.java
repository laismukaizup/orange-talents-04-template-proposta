package com.br.zup.academy.lais.proposta.SistemaCartao;

public class BloqueioResponse {
    public ResultadoBloqueio resultado;

    @Deprecated
    public BloqueioResponse(){}

    public BloqueioResponse(ResultadoBloqueio resultado) {
        this.resultado = resultado;
    }

    public ResultadoBloqueio getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoBloqueio resultado) {
        this.resultado = resultado;
    }
}
