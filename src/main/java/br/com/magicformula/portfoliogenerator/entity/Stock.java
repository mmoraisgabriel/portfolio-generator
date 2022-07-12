package br.com.magicformula.portfoliogenerator.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "roa")
    private Float roa;

    @Column(name = "pl")
    private Float pl;

    @Column(name = "roa_ranking")
    private Integer roaRanking;

    @Column(name = "pl_ranking")
    private Integer plRanking;

    @Column(name = "mf_ranking")
    private Integer mfRanking;

}
