package com.br.zup.academy.lais.proposta.SistemaCartao;

public enum ResultadoAssociaPayPal {
    ASSOCIADA, FALHA;

    public boolean estaOK() {
        if (this.equals(ASSOCIADA))
            return true;
        return false;
    }
}
