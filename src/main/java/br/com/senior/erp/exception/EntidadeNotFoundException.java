package br.com.senior.erp.exception;

public class EntidadeNotFoundException extends RuntimeException {
    public EntidadeNotFoundException(String msg) {
        super(msg);
    }
}
