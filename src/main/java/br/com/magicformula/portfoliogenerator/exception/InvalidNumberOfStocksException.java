package br.com.magicformula.portfoliogenerator.exception;

public class InvalidNumberOfStocksException extends RuntimeException {

    private static final long serialVersionUID = -4637215557873671743L;

    public InvalidNumberOfStocksException(String msg) {
        super(msg);
    }
}
