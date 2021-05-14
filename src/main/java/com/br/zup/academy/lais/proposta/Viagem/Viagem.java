package com.br.zup.academy.lais.proposta.Viagem;

import com.br.zup.academy.lais.proposta.Cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Viagem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @ManyToOne
    public Cartao cartao;
    @NotNull
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date dataTermino;
    @NotNull @DateTimeFormat
    public LocalDateTime dataCriacao;
    @NotEmpty
    public String ipCliente;
    @NotEmpty
    public String userAgent;

    public Viagem(Cartao cartao, Date dataTermino, LocalDateTime dataCriacao, String ipCliente, String userAgent) {
        this.cartao = cartao;
        this.dataTermino = dataTermino;
        this.dataCriacao = dataCriacao;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
    }
}
