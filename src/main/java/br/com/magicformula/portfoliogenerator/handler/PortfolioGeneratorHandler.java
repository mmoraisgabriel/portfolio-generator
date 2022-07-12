package br.com.magicformula.portfoliogenerator.handler;

import br.com.magicformula.portfoliogenerator.exception.InvalidNumberOfStocksException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class PortfolioGeneratorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidNumberOfStocksException.class)
    public ResponseEntity<Object> handleInsufficientFundsException(InvalidNumberOfStocksException ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions(Exception ex) {
        return ResponseEntity.internalServerError().body("Ops, algo de errado aconteceu.");
    }

}
