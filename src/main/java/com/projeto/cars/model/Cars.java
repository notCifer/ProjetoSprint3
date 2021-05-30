package com.projeto.cars.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Cars {
    
    public Cars() {
    }

    public Cars(@NotNull @NotEmpty String chassi, String nome, String marca, String cor, double valor,
            Date anoFabricacao) {
        this.chassi = chassi;
        this.nome = nome;
        this.marca = marca;
        this.cor = cor;
        this.valor = valor;
        this.ano_fabricacao = anoFabricacao;
    }

    @Id @Column(unique = true) @NotNull @NotEmpty
    private String chassi;
    @NotNull 
    private String nome;
    @NotNull 
    private String marca;
    @NotNull 
    private String cor;
    @NotNull 
    private double valor;
    @NotNull 
    private Date ano_fabricacao;

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getAnoFabricacao() {
        return ano_fabricacao;
    }

    public void setAnoFabricacao(Date anoFabricacao) {
        this.ano_fabricacao = anoFabricacao;
    }

}