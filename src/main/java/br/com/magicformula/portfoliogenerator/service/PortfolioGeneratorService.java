package br.com.magicformula.portfoliogenerator.service;

import br.com.magicformula.portfoliogenerator.dto.StockRequest;
import br.com.magicformula.portfoliogenerator.entity.Stock;
import br.com.magicformula.portfoliogenerator.exception.GenericException;
import br.com.magicformula.portfoliogenerator.exception.InvalidNumberOfStocksException;
import br.com.magicformula.portfoliogenerator.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PortfolioGeneratorService {

    @Autowired
    private StockRepository stockRepository;

    public String createStock(StockRequest stockRequest) {
        log.info("createStock() - [START] - stockRequest: {}", stockRequest);

        Stock stock = new Stock();
        stock.setCode(stockRequest.getCode());
        stock.setRoa(stockRequest.getRoa());
        stock.setPl(stockRequest.getPl());

        try {
            stockRepository.save(stock);
        } catch (Exception e) {
            log.info("createStock() - [ERROR] - Erro genérico - stockRequest: {}", stockRequest);
            throw new GenericException("Ops, tivemos um problema ao criar o ativo.");
        }

        log.info("createStock() - [END] - stockRequest: {}", stockRequest);
        return "Ativo criado com sucesso!";
    }

    public String editStock(StockRequest stockRequest) {
        log.info("editStock() - [START] - stockRequest: {}", stockRequest);

        Stock stock = findStock(stockRequest.getCode());

        stock.setPl(stockRequest.getPl());
        stock.setRoa(stockRequest.getRoa());

        try {
            stockRepository.save(stock);
        } catch (Exception e) {
            log.info("editStock() - [ERROR] - Erro genérico - stockRequest: {}", stockRequest);
            throw new GenericException("Ops, tivemos um problema ao editar o ativo.");
        }

        log.info("editStock() - [END] - stockRequest: {}", stockRequest);
        return "Edição feita com sucesso!";
    }

    public List<Stock> getStocks() {
        return stockRepository.findAll();
    }

    public String deleteStock(String code) {
        log.info("deleteStock() - [START] - code: {}", code);

        stockRepository.delete(findStock(code));

        log.info("deleteStock() - [END] - code: {}", code);
        return "Ativo deletado com sucesso!";
    }

    public List<String> generatePortfolio(Integer numberOfStocks) {
        log.info("generatePortfolio() - [START] - numberOfStocks: {}", numberOfStocks);

        if (numberOfStocks != 10
                && numberOfStocks != 15
                && numberOfStocks != 20
                && numberOfStocks != 25
                && numberOfStocks != 30) {
            log.info("generatePortfolio() - [ERROR] - Número de ativos não permitido - numberOfStocks: {}", numberOfStocks);
            throw new InvalidNumberOfStocksException("Número de ativos não permitido.");
        }

        calculateMfRanking();

        List<Stock> stockList = stockRepository.findAll(Sort.by(Sort.Direction.ASC, "mfRanking"));

        while (stockList.size() > numberOfStocks) {
            stockList.remove(stockList.size() - 1);
        }

        List<String> portfolio = new ArrayList<>();

        for (Stock stock : stockList) {
            portfolio.add(stock.getCode());
        }

        log.info("generatePortfolio() - [END] - portfolio: {}", portfolio);
        return portfolio;
    }

    private Stock findStock(String code) {
        log.info("findStock() - [START] - code: {}", code);

        Optional<Stock> stock = stockRepository.findById(code);

        if (stock.isEmpty()) {
            throw new GenericException("Ativo não encontrado!");
        }

        return stock.get();
    }

    private void calculateMfRanking() {
        log.info("calculateMfRanking() - [START]");

        calculateRoaRanking();
        calculatePlRanking();

        List<Stock> stockList = stockRepository.findAll();
        for (Stock stock : stockList) {
            stock.setMfRanking(stock.getRoaRanking() + stock.getPlRanking());
            stockRepository.save(stock);
        }

        log.info("calculateMfRanking() - [END]");
    }

    private void calculateRoaRanking() {
        log.info("calculateRoaRanking() - [START]");

        List<Stock> stockList = stockRepository.findAll(Sort.by(Sort.Direction.DESC, "roa"));
        for (Stock stock : stockList) {
            stock.setRoaRanking(stockList.indexOf(stock) + 1);
            stockRepository.save(stock);
        }

        log.info("calculateRoaRanking() - [END]");
    }

    private void calculatePlRanking() {
        log.info("calculatePlRanking() - [START]");

        List<Stock> stockList = stockRepository.findAll(Sort.by(Sort.Direction.ASC, "pl"));
        for (Stock stock : stockList) {
            stock.setPlRanking(stockList.indexOf(stock) + 1);
            stockRepository.save(stock);
        }

        log.info("calculatePlRanking() - [END]");
    }

}
