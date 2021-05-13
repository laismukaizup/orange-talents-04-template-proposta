package com.br.zup.academy.lais.proposta.SistemaCartao;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

@RestController
public class CartaoController {

    @PersistenceContext
    EntityManager manager;

    @PostMapping (value = "/bloqueioCartao/{id}")
    @Transactional
    public ResponseEntity bloquear(@PathVariable("id") Long idCartao, UriComponentsBuilder builder, HttpServletRequest request){

        Cartao cartao = manager.find(Cartao.class, idCartao);
        if(cartao == null)
            return ResponseEntity.notFound().build();
        if(!cartao.getAtivo())
            return ResponseEntity.unprocessableEntity().body("Cartão já bloqueado.");
        CartaoDadosBloqueio dadosBloqueio = new CartaoDadosBloqueio(request.getRemoteAddr(), request.getHeader("User-Agent"), cartao);
        cartao.setAtivo(false);
        manager.persist(cartao);
        manager.persist(dadosBloqueio);
        return ResponseEntity.ok().body("Cartão bloqueado");
    }

}
