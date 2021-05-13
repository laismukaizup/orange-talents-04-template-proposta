package com.br.zup.academy.lais.proposta.SistemaCartao;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class CartaoDadosBloqueio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataBloqueio = LocalDateTime.now();
    @NotEmpty
    private String ipCliente;
    @NotEmpty
    private  String userAgent;
    @ManyToOne
    private Cartao cartao;

    public CartaoDadosBloqueio(String ipCliente, String userAgent, Cartao cartao) {
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }
}
