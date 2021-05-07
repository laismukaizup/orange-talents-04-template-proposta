package com.br.zup.academy.lais.proposta.Validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {
    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ValidationErrorOutputRequest> handle(MethodArgumentNotValidException exception) {
        List<ValidationErrorOutputRequest> dto = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ValidationErrorOutputRequest erro = new ValidationErrorOutputRequest(e.getField(), mensagem);
            dto.add(erro);
        });
        return dto;
    }

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(PropostaException.class)
    public List<ValidationErrorOutputRequest> handle(PropostaException exception) {
        List<ValidationErrorOutputRequest> dto = new ArrayList<>();
        dto.add(new ValidationErrorOutputRequest("documento", exception.getMessage()));
        return dto;
    }
}
