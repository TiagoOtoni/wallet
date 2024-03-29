package com.Otoni.Wallet.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ativos")
public class Ativo {
    @Id
    @Column(unique = true)
    private String ticker;
    @Enumerated(EnumType.STRING)
    private TipoAtivo tipo;
    @OneToMany(mappedBy = "ativo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Compras> listaCompras = new ArrayList<>();

    public Ativo(){}

    public Ativo(String ticker, TipoAtivo tipo) {
        this.ticker = ticker;
        this.tipo = tipo;
    }

    public String getTicker() {
        return ticker;
    }

    public TipoAtivo getTipo() {
        return tipo;
    }

    public List<Compras> getListaCompras() {
        return listaCompras;
    }

    @Override
    public String toString() {
        return
                "\n" + "ticker = " + ticker +
                ", tipo = " + tipo +
                ", compras = " + "\n" + listaCompras
                ;

    }
}
