package com.br.zup.academy.lais.proposta.Proposta;

import com.br.zup.academy.lais.proposta.SistemFinanceiro.AvaliacaoRequest;
import com.br.zup.academy.lais.proposta.SistemFinanceiro.AvaliacaoRespose;
import com.br.zup.academy.lais.proposta.SistemFinanceiro.FinanceiroClient;
import com.br.zup.academy.lais.proposta.SistemFinanceiro.StatusAnalise;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/proposta")
public class PropostaController {

    @PersistenceContext
    EntityManager manager;

    @Autowired
    ProibeDocumentoIgualParaProposta proibeDocumentoIgualParaProposta;

    @Autowired
    FinanceiroClient financeiroClient;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(proibeDocumentoIgualParaProposta);
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder uriBuilder) {

        URI uri = uriBuilder.path("/proposta").buildAndExpand().toUri();
        Proposta proposta = request.toModel();
        manager.persist(proposta);
        proposta = avaliaProposta(proposta);
        manager.merge(proposta);

        return ResponseEntity.created(uri).body(proposta);
    }

    public Proposta avaliaProposta(Proposta proposta) {
        try {
            AvaliacaoRespose avaliacao = financeiroClient.avaliacao(new AvaliacaoRequest(proposta));
            proposta.setStatus(StatusAnalise.converter(avaliacao.getResultadoSolicitacao()));
        } catch (FeignException.UnprocessableEntity ex) {
            proposta.setStatus(StatusProposta.ELEGIVEL);
        }
        return proposta;
    }
}
