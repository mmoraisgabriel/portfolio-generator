package br.com.magicformula.portfoliogenerator.dto;

import lombok.Data;

@Data
public class StockRequest {

    private String code;
    private Float roa;
    private Float pl;

}
