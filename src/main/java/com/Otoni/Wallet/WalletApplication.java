package com.Otoni.Wallet;

import com.Otoni.Wallet.main.Main;
import com.Otoni.Wallet.repository.AtivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WalletApplication implements CommandLineRunner {

@Autowired
	private AtivoRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(WalletApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(repository);
		main.displayMenu();
	}

}
