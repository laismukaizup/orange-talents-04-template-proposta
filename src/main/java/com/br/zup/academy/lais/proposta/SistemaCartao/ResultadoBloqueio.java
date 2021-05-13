package com.br.zup.academy.lais.proposta.SistemaCartao;

public enum ResultadoBloqueio {
    BLOQUEADO, FALHA;

    public boolean converter() {
        if(this.equals(BLOQUEADO))
            return true;
        return false;
    }
}
