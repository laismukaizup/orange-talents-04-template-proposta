package com.br.zup.academy.lais.proposta.Viagem;

import com.br.zup.academy.lais.proposta.SistemaCartao.Cartao;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

public class ViagemRequest {
    @NotBlank
    public String destino;
    @Future
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date dataTermino;
    @NotNull @DateTimeFormat
    public LocalDateTime dataCriacao = LocalDateTime.now();
    public String ipCliente;
    public String userAgent;

    public ViagemRequest(String destino, Date dataTermino, String ipCliente, String userAgent) {
        this.destino = destino;
        this.dataTermino = dataTermino;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
    }

    public void setIpCliente(String ipCliente) {
        this.ipCliente = ipCliente;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Viagem toModel(Cartao cartao) {
        return new Viagem(cartao, dataTermino, dataCriacao, ipCliente, userAgent);
    }
}
