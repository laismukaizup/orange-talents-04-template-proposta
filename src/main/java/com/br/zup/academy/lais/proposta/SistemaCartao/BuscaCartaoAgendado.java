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
    CartaoClient cartaoClient;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        List<Optional<Proposta>> propostaLista = propostaRepository.findByStatusAndCartao(StatusProposta.ELEGIVEL, null);
        for (Optional<Proposta> possivelProposta : propostaLista) {
            salvaCartao(possivelProposta.get());
        }
    }

    private void salvaCartao(Proposta proposta) {
        try {
            CartaoRespose cartao = cartaoClient.retornaNumeroCartao(proposta.id.toString());
            proposta.setCartao(cartao.getId());
            propostaRepository.save(proposta);
        }
        catch (FeignException.BadRequest badRequest){

        }
        catch (FeignException.InternalServerError ex){
            
        }
    }
}
