package com.example.cardealer.repository;

import com.example.cardealer.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findCarsByMark(String mark);

    List<Car> findCarsByModel(String model);

    @Query("select distinct c.mark from Car c order by c.mark asc")
    List<String> findMark();

    @Query("select distinct c.model from Car c order by c.model asc")
    List<String> findModel();

    @Query("select distinct c.productionYear from Car c order by c.productionYear asc")
    List<Integer> findProductionYear();
}
