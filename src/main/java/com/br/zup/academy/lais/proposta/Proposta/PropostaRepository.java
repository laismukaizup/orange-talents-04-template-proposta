package com.br.zup.academy.lais.proposta.Proposta;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PropostaRepository extends CrudRepository<Proposta, Long> {
    Optional<Proposta> findByDocumento(String documento);

    List<Optional<Proposta>> findByStatusAndCartao(StatusProposta status, String cartao);
}
