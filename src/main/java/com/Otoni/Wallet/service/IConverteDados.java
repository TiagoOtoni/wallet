package com.Otoni.Wallet.service;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}
