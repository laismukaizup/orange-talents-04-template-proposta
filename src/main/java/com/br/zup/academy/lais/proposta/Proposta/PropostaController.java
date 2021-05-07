package com.br.zup.academy.lais.proposta.Proposta;

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

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(proibeDocumentoIgualParaProposta);
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder uriBuilder) {
        Proposta p = request.toModel();
        manager.persist(p);

        URI uri = uriBuilder.path("/proposta").buildAndExpand().toUri();
        return ResponseEntity.created(uri).body(request);
    }
}
