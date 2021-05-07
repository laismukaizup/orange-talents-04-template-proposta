package com.br.zup.academy.lais.proposta.Proposta;

import com.br.zup.academy.lais.proposta.Validacao.PropostaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class ProibeDocumentoIgualParaProposta implements Validator {

    @Autowired
    PropostaRepository propostaRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return PropostaRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors())
            return;

        PropostaRequest request = (PropostaRequest) target;
        if (!request.documentoValido())
            errors.rejectValue("documento", null, "documento precisa ser cpf ou cnpj");

        Optional<Proposta> possivelProposta = propostaRepository.findByDocumento(request.removeMascara(request.documento));

        if (possivelProposta.isPresent())
            throw new PropostaException("Já existe uma proposta para esse CPF / CNPJ");
    }
}
