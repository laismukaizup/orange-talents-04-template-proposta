package com.br.zup.academy.lais.proposta.Cartao;

import org.springframework.data.repository.CrudRepository;

public interface CartaoRepository extends CrudRepository<Cartao, Long> {
    Cartao findByNumero(String numeroCartao);
}
