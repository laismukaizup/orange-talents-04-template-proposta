package com.br.zup.academy.lais.proposta.Biometria;

import com.br.zup.academy.lais.proposta.Cartao.Cartao;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/biometria")
public class BiometriaController {

    @PersistenceContext
    EntityManager manager;

    @PostMapping(value = "/{id}")
    @Transactional
    public ResponseEntity cadastrar(@PathVariable("id") Long idCartao, @RequestBody @Valid BiometriaRequest biometriaRequest,
                                    UriComponentsBuilder uriBuilder) {
        Cartao cartao = manager.find(Cartao.class, idCartao);
        Biometria biometria = biometriaRequest.toModel(cartao);
        manager.persist(biometria);

        URI uri = uriBuilder.path("/biometria").buildAndExpand().toUri();

        return ResponseEntity.created(uri).body(biometria.toString());
    }
}
