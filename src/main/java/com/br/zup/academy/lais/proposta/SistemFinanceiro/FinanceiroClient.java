package com.br.zup.academy.lais.proposta.SistemFinanceiro;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "solicitacao", url = "${financeiro.dominio}")
@Component
public interface FinanceiroClient {

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public AvaliacaoRespose avaliacao(AvaliacaoRequest request);
}
