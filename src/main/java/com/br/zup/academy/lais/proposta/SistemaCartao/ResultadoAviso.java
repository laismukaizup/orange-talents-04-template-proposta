package com.br.zup.academy.lais.proposta.SistemaCartao;

public enum ResultadoAviso {
    CRIADO,FALHA;

    public boolean estaOK() {
        if(this.equals(CRIADO))
            return true;
        return false;
    }
}
