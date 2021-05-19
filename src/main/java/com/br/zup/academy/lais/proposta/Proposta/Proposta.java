package com.br.zup.academy.lais.proposta.Proposta;

import com.br.zup.academy.lais.proposta.EncryptToDatabase;

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
    @Column(unique = true)  @Convert(converter = EncryptToDatabase.class)
    public String documento;
    @NotEmpty
    public String nome;
    @NotEmpty
    @Email
    public String email;
    @NotEmpty
    public String endereco;
    @NotNull
    @Positive
    public BigDecimal salario;
    public boolean flagCartaoOK;
    public StatusProposta status;


    @Deprecated
    public Proposta() {
    }

    public Proposta(String documento,String nome, String email, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public void setStatus(StatusProposta status) {
        this.status = status;
    }

    public void setFlagCartaoOK(boolean flagCartaoOK) {
        this.flagCartaoOK = flagCartaoOK;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusProposta getStatus() {
        return status;
    }

}
