package com.br.zup.academy.lais.proposta.SistemaCartao;

import com.br.zup.academy.lais.proposta.Proposta.Proposta;
import com.br.zup.academy.lais.proposta.Proposta.PropostaRepository;
import com.br.zup.academy.lais.proposta.Proposta.StatusProposta;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BuscaCartaoAgendado {
    @Autowired
    PropostaRepository propostaRepository;
    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    CartaoClient cartaoClient;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        List<Optional<Proposta>> possiveisPropostas = propostaRepository.findByStatusAndFlagCartaoOK(StatusProposta.ELEGIVEL, false);
        for (Optional<Proposta> possivelProposta: possiveisPropostas) {
            Proposta proposta = possivelProposta.get();
            salvaCartao(proposta);
        }
    }

    private void salvaCartao(Proposta proposta) {
        try {
            CartaoRespose cartaoRespose = cartaoClient.retornaNumeroCartao(proposta.id.toString());
            Cartao cartao = cartaoRespose.toModel(proposta);
            cartaoRepository.save(cartao);
            proposta.setFlagCartaoOK(true);
            propostaRepository.save(proposta);
        }
        catch (FeignException.BadRequest badRequest){

        }
        catch (FeignException.InternalServerError ex){
            
        }
    }
}
