package com.Otoni.Wallet.service;



import com.Otoni.Wallet.model.Results;

import java.util.Scanner;

public class Search {
    GetResponseUrl getResponseUrl = new GetResponseUrl();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://brapi.dev/api/quote/";
    private final String API_KEY = System.getenv("ApiKeyBrapi");

    public double findPrice(String token) {

        String url = ENDERECO + token + "?token=" + API_KEY;
        String json = getResponseUrl.getData(url);
        Results container = conversor.obterDados(json, Results.class);

        return container.results().get(0).regularMarketPrice();

    }


}
