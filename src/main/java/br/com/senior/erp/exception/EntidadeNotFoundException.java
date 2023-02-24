package br.com.senior.erp.exception;

import java.util.UUID;

public class EntidadeNotFoundException extends RuntimeException {
    public EntidadeNotFoundException(String nomeObjeto, UUID id) {
        super(nomeObjeto + " nao foi encontrado para o ID: " + id);
    }

    public EntidadeNotFoundException(String msg) {
        super(msg);
    }
}
