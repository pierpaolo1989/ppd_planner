package com.soa.ppd_planner;

import com.soa.ppd_planner.dao.CoinRepository;
import com.soa.ppd_planner.model.Coin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PpdPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PpdPlannerApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(CoinRepository coinRepository) throws Exception {
		return (args) -> {
			// save a couple of customers
			coinRepository.save(new Coin(0.20, "Italia", 2012));
			coinRepository.save(new Coin(1, "Italia", 2012));
			coinRepository.save(new Coin(2, "Italia", 2014));
			coinRepository.save(new Coin(0.01, "Belgio", 2012));
			coinRepository.save(new Coin(0.10, "Eire", 2019));
		};
	}
}
