package com.br.zup.academy.lais.proposta.Proposta;

import com.br.zup.academy.lais.proposta.SistemFinanceiro.AvaliacaoRequest;
import com.br.zup.academy.lais.proposta.SistemFinanceiro.FinanceiroClient;
import feign.FeignException;
import io.opentracing.Span;
import io.opentracing.Tracer;
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

    private final Tracer tracer;

    public PropostaController(Tracer tracer) {
        this.tracer = tracer;
    }


    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder uriBuilder) {

        Span activeSpan = tracer.activeSpan();
        activeSpan.setBaggageItem("user.email", "luram.archanjo@zup.com.br");

        URI uri = uriBuilder.path("/proposta").buildAndExpand().toUri();
        Proposta proposta = request.toModel();
        manager.persist(proposta);
        proposta = avaliaProposta(proposta);
        manager.merge(proposta);

        return ResponseEntity.created(uri).body(proposta);
    }


    @GetMapping(value = "{id}")
    public ResponseEntity detalhar(@PathVariable("id") Long id) {
        Proposta proposta = manager.find(Proposta.class, id);
        if (proposta == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(new PropostaDetalheResponse(proposta.getStatus().name()));
    }

    public Proposta avaliaProposta(Proposta proposta) {
        try {
            financeiroClient.avaliacao(new AvaliacaoRequest(proposta));
            proposta.setStatus(StatusProposta.ELEGIVEL);
            proposta.setFlagCartaoOK(false);
        } catch (FeignException.UnprocessableEntity ex) {
            proposta.setStatus(StatusProposta.NAO_ELEGIVEL);
            proposta.setFlagCartaoOK(true);
        }
        return proposta;
    }


}
