package com.br.zup.academy.lais.proposta.SistemaCartao;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AvisoRequest {
    public String destino;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date validoAte;

    public AvisoRequest(String destino, Date validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }
}
