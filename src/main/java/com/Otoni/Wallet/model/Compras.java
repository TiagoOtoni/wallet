package com.Otoni.Wallet.model;

import jakarta.persistence.*;

@Entity
@Table(name = "compras")
public class Compras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;
    private double preco;
    private int quantidade;
    private double emolumentos;
    private double liquido = 0;
    @ManyToOne
    private Ativo ativo;

    public Compras(){}

    public Compras(String data, double preco, int quantidade, double emolumentos) {
        this.data = data;
        this.preco = preco;
        this.quantidade = quantidade;
        this.emolumentos = emolumentos;
        calculaLiquidoEmolumentos();
    }

    public String getData() {
        return data;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getEmolumentos() {
        return emolumentos;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    public double getLiquido() {
        return liquido;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }
    private void calculaLiquidoEmolumentos(){
        this.liquido = (this.quantidade * this.preco) + this.emolumentos;
    }

    @Override
    public String toString() {
        return
                "data = " + data  +
                ", preco = " + preco +
                ", quantidade = " + quantidade +
                ", emolumentos = " + emolumentos + "\n";

    }
}
