package br.com.senior.erp.exception;

public class NegocioException extends RuntimeException {

    public NegocioException(String msg) {
        super(msg);
    }
}
