package com.soa.ppd_planner.dao;

import com.soa.ppd_planner.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoinRepository extends JpaRepository<Coin, Long> {

    List<Coin> findByCountry(String country);
}
