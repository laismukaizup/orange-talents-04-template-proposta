package com.br.zup.academy.lais.proposta.Cartao;

import com.br.zup.academy.lais.proposta.Carteira.Carteira;
import com.br.zup.academy.lais.proposta.Carteira.CarteiraRequest;
import com.br.zup.academy.lais.proposta.Carteira.CarteiraResponse;
import com.br.zup.academy.lais.proposta.SistemaCartao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class CartaoController {

    @PersistenceContext
    EntityManager manager;

    @Autowired
    CartaoClient cartaoClient;


    @PostMapping (value = "/bloqueioCartao/{id}")
    @Transactional
    public ResponseEntity bloquear(@PathVariable("id") Long idCartao, UriComponentsBuilder builder, HttpServletRequest request) {

        Cartao cartao = manager.find(Cartao.class, idCartao);
        if (cartao == null)
            return ResponseEntity.notFound().build();
        if (!cartao.getAtivo())
            return ResponseEntity.unprocessableEntity().body("Cartão já bloqueado.");

        Boolean sistemaBancarioOK = notificaSistemaBancarioDoBloqueio(cartao.getNumero());
        if (!sistemaBancarioOK)
            return ResponseEntity.badRequest().body("API retornou erro");

        CartaoDadosBloqueio dadosBloqueio = new CartaoDadosBloqueio(request.getRemoteAddr(), request.getHeader("User-Agent"), cartao);
        cartao.setAtivo(false);
        manager.persist(cartao);
        manager.persist(dadosBloqueio);
        return ResponseEntity.ok().body("Cartão bloqueado");
    }

    @PostMapping(value = "/associaPaypal/{id}")
    @Transactional
    public ResponseEntity associarSistemaPaypal(@PathVariable("id") Long idCartao, @RequestBody @Valid CarteiraRequest carteiraRequest,
                                                UriComponentsBuilder uriBuilder) {

        Cartao cartao = manager.find(Cartao.class, idCartao);
        if (cartao == null)
            return ResponseEntity.notFound().build();

        boolean resultadoOK = verificaResultadoDaAssociacaoPaypal(cartao.getNumero(), carteiraRequest);
        if (!resultadoOK)
            return ResponseEntity.badRequest().body("API retornou erro");

        Carteira carteira = carteiraRequest.toModel(cartao);
        manager.persist(carteira);
        cartao.setAssociadoPaypal(true);
        manager.persist(cartao);

        URI uri = uriBuilder.path("/proposta").buildAndExpand().toUri();
        return ResponseEntity.created(uri).body("Cartão associado");
    }

    private boolean verificaResultadoDaAssociacaoPaypal(String numeroCartao, CarteiraRequest carteiraRequest) {
        CarteiraResponse carteiraResponse = cartaoClient.retornaResultadoPaypalAssociado(numeroCartao, carteiraRequest);
        return carteiraResponse.getResultado().estaOK();
    }

    private Boolean notificaSistemaBancarioDoBloqueio(String numeroCartao) {
        BloqueioResponse bloqueioResponse = cartaoClient.retornaSituacaoCartaoBloqueado(numeroCartao, new BloqueioRequest("Proposta API"));

        return bloqueioResponse.getResultado().converter();
    }
}
