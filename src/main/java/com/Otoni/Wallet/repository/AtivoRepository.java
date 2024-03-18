package com.Otoni.Wallet.repository;

import com.Otoni.Wallet.model.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AtivoRepository extends JpaRepository<Ativo, Long> {
    Optional<Ativo> findByTickerContainingIgnoreCase(String ticker);
    List<Ativo> findAll();



}
