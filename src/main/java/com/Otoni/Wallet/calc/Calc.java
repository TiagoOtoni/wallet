package com.Otoni.Wallet.calc;

import com.Otoni.Wallet.model.Ativo;
import com.Otoni.Wallet.model.Compras;
import com.Otoni.Wallet.service.Search;

import java.text.NumberFormat;

import java.util.Locale;
import java.util.Optional;

public class Calc {

    Locale localBrasil = new Locale("pt", "BR");
    Search search = new Search();
    private int quantidadeTotal = 0;
    private double liquidoTotal = 0;
    private double precoMedio = 0;
    private double precoMercado = 0;
    private double valorMercado = 0;
    private double lucro = 0;

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public double getLiquidoTotal() {
        return liquidoTotal;
    }

    public double getPrecoMedio() {
        return precoMedio;
    }

    public double getPrecoMercado() {
        return precoMercado;
    }

    public double getValorMercado() {
        return valorMercado;
    }

    public double getLucro() {
        return lucro;
    }

    private String formatCurreny(double value){
        return NumberFormat.getCurrencyInstance(localBrasil).format(value);
    }

    public String calculaTotal (Optional<Ativo> ativo){
        double precoApi = search.findPrice(ativo.get().getTicker());

        var ativoCompras = ativo.get().getListaCompras();

        for (Compras i : ativoCompras){
            quantidadeTotal += i.getQuantidade();
            liquidoTotal += i.getLiquido();
        }

        precoMedio = liquidoTotal / quantidadeTotal;
        precoMercado = precoApi;
        valorMercado = quantidadeTotal * precoApi;
        lucro = valorMercado - liquidoTotal;


        return toString();

    }

    public void clear (){
        quantidadeTotal = 0;
        liquidoTotal = 0;
        precoMedio = 0;
        precoMercado = 0;
        valorMercado = 0;
        lucro = 0;
    }


    @Override
    public String toString() {
        return
                "quantidadeTotal= " + getQuantidadeTotal() +
                ", liquidoTotal= " + formatCurreny(getLiquidoTotal()) +
                ", precoMedio= " + formatCurreny(getPrecoMedio()) +
                ", precoMercado= " + formatCurreny(getPrecoMercado()) +
                ", valorMercado= " + formatCurreny(getValorMercado()) +
                ", lucro caso venda= " + formatCurreny(getLucro())
                ;
    }
}
