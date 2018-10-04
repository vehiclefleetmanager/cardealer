package com.example.cardealer.repository;

import com.example.cardealer.model.Trader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraderRepository extends JpaRepository<Trader, Integer> {
}
