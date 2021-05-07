package com.br.zup.academy.lais.proposta.SistemFinanceiro;

import com.br.zup.academy.lais.proposta.Proposta.StatusProposta;

public enum StatusAnalise {
    COM_RESTRICAO, SEM_RESTRICAO;

    public static StatusProposta converter(StatusAnalise status) {
        if (status.equals(COM_RESTRICAO))
            return StatusProposta.NAO_ELEGIVEL;
        return StatusProposta.ELEGIVEL;
    }
}
