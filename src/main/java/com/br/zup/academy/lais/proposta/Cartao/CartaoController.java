package com.br.zup.academy.lais.proposta.Cartao;

import com.br.zup.academy.lais.proposta.SistemaCartao.BloqueioRequest;
import com.br.zup.academy.lais.proposta.SistemaCartao.BloqueioResponse;
import com.br.zup.academy.lais.proposta.SistemaCartao.CartaoClient;
import com.br.zup.academy.lais.proposta.SistemaCartao.CartaoDadosBloqueio;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    CartaoClient cartaoClient;


    @PostMapping (value = "/bloqueioCartao/{id}")
    @Transactional
    public ResponseEntity bloquear(@PathVariable("id") String id, UriComponentsBuilder builder, HttpServletRequest request) {

        Cartao cartao = manager.find(Cartao.class, id);
        if (cartao == null)
            return ResponseEntity.notFound().build();
        if (!cartao.getAtivo())
            return ResponseEntity.unprocessableEntity().body("Cartão já bloqueado.");

        Boolean sistemaBancarioOK = notificaSistemaBancarioDoBloqueio(cartao.getId());
        if (!sistemaBancarioOK)
            return ResponseEntity.badRequest().body("API retornou erro");

        CartaoDadosBloqueio dadosBloqueio = new CartaoDadosBloqueio(request.getRemoteAddr(), request.getHeader("User-Agent"), cartao);
        cartao.setAtivo(false);
        manager.persist(cartao);
        manager.persist(dadosBloqueio);
        return ResponseEntity.ok().body("Cartão bloqueado");
    }

    private Boolean notificaSistemaBancarioDoBloqueio(String numeroCartao) {
        BloqueioResponse bloqueioResponse = cartaoClient.retornaSituacaoCartaoBloqueado(numeroCartao, new BloqueioRequest("Proposta API"));

        return bloqueioResponse.getResultado().converter();
    }
}
