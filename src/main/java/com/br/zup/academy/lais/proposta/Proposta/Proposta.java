package com.br.zup.academy.lais.proposta.Proposta;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotEmpty
    @Column(unique = true)
    public String documento;
    @NotEmpty
    @Email
    public String email;
    @NotEmpty
    public String endereco;
    @NotNull
    @Positive
    public BigDecimal salario;

    @Deprecated
    public Proposta() {
    }

    public Proposta(String documento, String email, String endereco, BigDecimal salario) {

        ;
        this.documento = documento;
        this.email = email;
        this.endereco = endereco;
        this.salario = salario;
    }

}
