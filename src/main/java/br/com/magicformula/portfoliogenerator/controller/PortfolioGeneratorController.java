package br.com.magicformula.portfoliogenerator.controller;

import br.com.magicformula.portfoliogenerator.dto.StockRequest;
import br.com.magicformula.portfoliogenerator.entity.Stock;
import br.com.magicformula.portfoliogenerator.service.PortfolioGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PortfolioGeneratorController {

    @Autowired
    private PortfolioGeneratorService service;

    @GetMapping(value = "/portfolio", produces = "application/json")
    public ResponseEntity<List<String>> generatePortfolio(@RequestParam Integer numberOfStocks) {
        return ResponseEntity.ok().body(service.generatePortfolio(numberOfStocks));
    }

    @PostMapping(value = "/new", consumes = "application/json")
    public ResponseEntity<Object> createStock(@RequestBody StockRequest stockRequest) {
        service.createStock(stockRequest);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping(value = "/edit", consumes = "application/json")
    public ResponseEntity<Object> editStock(@RequestBody StockRequest stockRequest) {
        return service.editStock(stockRequest);
    }

    @GetMapping(value = "/stocks", produces = "application/json")
    public List<Stock> getStocks() {
        return service.getStocks();
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deleteStock(@RequestParam String code) {
        return service.deleteStock(code);
    }

}
