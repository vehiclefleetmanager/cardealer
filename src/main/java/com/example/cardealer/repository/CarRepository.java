package com.example.cardealer.repository;

import com.example.cardealer.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findCarsByMark(String mark);

    List<Car> findCarsByModel(String model);

    @Query("select distinct c.mark from Car c order by c.mark asc")
    List<String> findMark();

    @Query("select distinct c.model from Car c order by c.model asc")
    List<String> findModel();

    @Query("select c from Car c where c.ownerId =?1")
    List<Car> findCarsByOwnerId(Integer ownerId);

    @Query("select c from Car c where c.status=?1 and c.mark=?2 or c.model =?3 " +
            "or c.productionYear between ?4 and ?5 or c.price between ?6 and ?7 ")
    List<Car> findCarsFromSearchButton(Car.Status status, String carMark, String carModel,
                                       Integer fromYear, Integer toYear,
                                       BigDecimal fromPrice, BigDecimal toPrice);

    @Query("select distinct c.productionYear from Car c order by c.productionYear asc")
    List<Integer> findProductionYear();

    @Query("select c from Car c where c.status = ?1")
    List<Car> findCarsByStatusIsAvailable(Car.Status status);

    @Query("select c from Car c where c.regNumber = ?1")
    Optional<Car> findCarByRegNumber(String regNumber);

    @Query("select c from Car c where c.bodyNumber = ?1")
    Optional<Car> findCarByBodyNumber(String bodyNumber);

    @Transactional
    @Modifying
    @Query("delete from Car c where c.regNumber = ?1")
    void deleteByRegNumber(String regNumber);
}
