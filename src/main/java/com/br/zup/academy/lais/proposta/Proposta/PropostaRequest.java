package com.br.zup.academy.lais.proposta.Proposta;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PropostaRequest {
    @NotEmpty
    @Column(unique = true)
    public String documento;
    @NotEmpty
    @Column(unique = true)
    public String nome;
    @NotEmpty @Email
    public String email;
    @NotEmpty
    public String endereco;
    @NotNull
    @Positive
    public BigDecimal salario;

    @Deprecated
    public PropostaRequest(){}

    public PropostaRequest(String documento,String nome, String email, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.salario = salario;
    }

    public  Proposta toModel() {
        return new Proposta(removeMascara(documento),nome, email,endereco,salario);
    }

    public boolean documentoValido()
    {
        CPFValidator cpfValidator = new CPFValidator();
        cpfValidator.initialize(null);

        CNPJValidator cnpjValidator = new CNPJValidator();
        cnpjValidator.initialize(null);

        return cpfValidator.isValid(documento, null)
                || cnpjValidator.isValid(documento, null);
    }


    public String removeMascara(String documento) {
        return documento.replaceAll("\\D", "");
    }

}
