package com.br.zup.academy.lais.proposta.Viagem;

import com.br.zup.academy.lais.proposta.Cartao.Cartao;
import com.br.zup.academy.lais.proposta.SistemaCartao.AvisoRequest;
import com.br.zup.academy.lais.proposta.SistemaCartao.AvisoRespose;
import com.br.zup.academy.lais.proposta.SistemaCartao.CartaoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/viagem")
public class ViagemController {

    @PersistenceContext
    EntityManager manager;

    @Autowired
    CartaoClient cartaoClient;

    @PostMapping(value = "/{id}")
    @Transactional
    public ResponseEntity cadastrar(@PathVariable("id") Long idCartao, @RequestBody @Valid ViagemRequest viagemRequest,
                                    HttpServletRequest request, UriComponentsBuilder uriBuilder){
        Cartao cartao = manager.find(Cartao.class, idCartao);
        if(cartao == null)
            return ResponseEntity.notFound().build();
        else if(!cartao.getAtivo())
            return ResponseEntity.unprocessableEntity().body("Viagem não pode ser salva para um cartão cancelado");

        if(!verificaAvisoAoSistemaBacario(cartao.id, new AvisoRequest(viagemRequest.destino, viagemRequest.dataTermino)))
            return  ResponseEntity.unprocessableEntity().body("Erro ao processar os dados na API Externa");

        viagemRequest.setIpCliente(request.getRemoteAddr());
        viagemRequest.setUserAgent( request.getHeader("User-Agent"));
        Viagem viagem = viagemRequest.toModel(cartao);
        manager.persist(viagem);

        URI uri = uriBuilder.path("/proposta").buildAndExpand().toUri();
        return  ResponseEntity.created(uri).body("Notificação de viagem criada");
    }

    private boolean verificaAvisoAoSistemaBacario(String numeroCartao, AvisoRequest avisoRequest) {
        AvisoRespose avisoRespose = cartaoClient.retornaResultadoDoAvisoAoSistemaBancario(numeroCartao, avisoRequest);
        return avisoRespose.getResultado().estaOK();
    }
}
