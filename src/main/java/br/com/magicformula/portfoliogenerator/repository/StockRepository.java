package br.com.magicformula.portfoliogenerator.repository;

import br.com.magicformula.portfoliogenerator.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

}
