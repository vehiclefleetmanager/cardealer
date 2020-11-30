package com.example.cardealer.cars.boundary;

import com.example.cardealer.cars.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select c from Car c where c.status = ?1")
    Page<Car> findCarsByStatus(Pageable pageable, Car.Status status);

    @Query("select c from Car c where c.carOwner.id =?1")
    List<Car> findAllCarsOfCustomer(Long id);

    @Query("select c from Car c where c.carOwner.id = ?1")
    Page<Car> findAllCarsOfCustomer(Long userId, Pageable pageable);

    @Query("select c from Car c where c.status = ?1")
    List<Car> findCarsByStatus(Car.Status status);

    @Query("select distinct c.mark from Car c order by c.mark asc")
    List<String> findMark();

    @Query("select distinct c.model from Car c order by c.model asc")
    List<String> findModel();


    @Query("select c from Car c where c.mark=?1 or c.productionYear <= ?2 " +
            "or c.price <= ?3 or c.status = ?4")
    List<Car> findCarsFromSearchButton(String carMark, Integer productionYear,
                                       BigDecimal price, Car.Status status);

    @Query("select c from Car c where c.mark=?1 or c.productionYear <= ?2 " +
            "or c.price <= ?3 or c.status = ?4")
    Page<Car> findCarsFromSearchButton(String carMark, int maxYearValue, BigDecimal maxPriceValue,
                                       Car.Status status, Pageable pageable);

    @Query("select distinct c.productionYear from Car c order by c.productionYear asc")
    List<Integer> findProductionYear();

    @Query("select c from Car c where c.mark=?1 or c.productionYear <= ?2 " +
            "or c.price <= ?3 and c.status = 'AVAILABLE'")
    List<Car> findCarsFromSearchButton(String carMark, Integer productionYear,
                                       BigDecimal price);

}
