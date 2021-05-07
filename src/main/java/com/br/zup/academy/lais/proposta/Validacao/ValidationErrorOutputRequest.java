package com.br.zup.academy.lais.proposta.Validacao;

public class ValidationErrorOutputRequest {
    private String campo;
    private String erro;

    public ValidationErrorOutputRequest(String campo, String erro) {
        super();
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }
    public String getErro() {
        return erro;
    }
}
