package com.Otoni.Wallet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Token(String symbol , double regularMarketPrice) {
}
