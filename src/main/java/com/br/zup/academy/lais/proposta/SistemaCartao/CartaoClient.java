package com.br.zup.academy.lais.proposta.SistemaCartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "cartao", url = "${cartao.dominio}")
@Component
public interface CartaoClient {
    @RequestMapping(method = RequestMethod.GET)
    public CartaoRespose retornaNumeroCartao(@PathVariable("idProposta") String idProposta);

}
