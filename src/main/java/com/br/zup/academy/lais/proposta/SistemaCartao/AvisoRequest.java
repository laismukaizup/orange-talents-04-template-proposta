package com.br.zup.academy.lais.proposta.SistemaCartao;

import java.util.Date;

public class AvisoRequest {
    public String destino;
    public Date validoAte;

    public AvisoRequest(String destino, Date validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }
}
