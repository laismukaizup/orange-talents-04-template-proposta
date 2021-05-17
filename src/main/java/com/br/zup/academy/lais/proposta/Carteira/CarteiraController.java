package com.br.zup.academy.lais.proposta.Carteira;

import com.br.zup.academy.lais.proposta.Cartao.Cartao;
import com.br.zup.academy.lais.proposta.SistemaCartao.CartaoClient;
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
import javax.validation.Valid;
import java.net.URI;

@RestController
public class CarteiraController {

    @PersistenceContext
    EntityManager manager;

    @Autowired
    CartaoClient cartaoClient;

    @PostMapping(value = "/associaPaypal/{id}")
    @Transactional
    public ResponseEntity associarSistemaPaypal(@PathVariable("id") Long idCartao, @RequestBody @Valid CarteiraRequest carteiraRequest,
                                                UriComponentsBuilder uriBuilder) {
        return associa(idCartao, carteiraRequest, uriBuilder, TipoCarteira.PAYPAL);
    }


    @PostMapping(value = "/associaSamsumgPay/{id}")
    @Transactional
    public ResponseEntity associarSistemaSamsungPay(@PathVariable("id") Long idCartao, @RequestBody @Valid CarteiraRequest carteiraRequest,
                                                UriComponentsBuilder uriBuilder) {
        return associa(idCartao, carteiraRequest, uriBuilder, TipoCarteira.SAMSUNG_PAY);
    }

    private ResponseEntity associa(Long idCartao, CarteiraRequest carteiraRequest, UriComponentsBuilder uriBuilder,
                               TipoCarteira tipoCarteira) {

        Cartao cartao = manager.find(Cartao.class, idCartao);
        if (cartao == null)
            return ResponseEntity.notFound().build();

        boolean resultadoOK = verificaResultadoDaAssociacao(cartao.getNumero(), carteiraRequest);
        if (!resultadoOK)
            return ResponseEntity.badRequest().body("API retornou erro");

        Carteira carteira = carteiraRequest.toModel(cartao, tipoCarteira);
        manager.persist(carteira);
        cartao.setTipoCarteira(tipoCarteira);
        manager.persist(cartao);

        URI uri = uriBuilder.path("/proposta").buildAndExpand().toUri();
        return ResponseEntity.created(uri).body("Cartão associado");
    }

    private boolean verificaResultadoDaAssociacao(String numeroCartao, CarteiraRequest carteiraRequest) {
        CarteiraResponse carteiraResponse = cartaoClient.retornaResultadoAssociado(numeroCartao, carteiraRequest);
        return carteiraResponse.getResultado().estaOK();
    }
}
