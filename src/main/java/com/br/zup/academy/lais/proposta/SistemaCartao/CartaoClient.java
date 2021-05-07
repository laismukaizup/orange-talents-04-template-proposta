package com.br.zup.academy.lais.proposta.SistemaCartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "cartao", url = "http://localhost:8888")
@Component
public interface CartaoClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/cartoes?idProposta={id}")
    public CartaoRespose retornaNumeroCartao(@PathVariable("idProposta") String idProposta);

}
