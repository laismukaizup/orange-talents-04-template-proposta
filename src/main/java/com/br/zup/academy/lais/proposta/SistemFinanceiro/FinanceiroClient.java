package com.br.zup.academy.lais.proposta.SistemFinanceiro;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "solicitacao", url = "http://localhost:9999")
@Component
public interface FinanceiroClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/solicitacao", produces = "application/json")
    public AvaliacaoRespose avaliacao(AvaliacaoRequest request);
}
