package com.example.cardealer.repairs.boundary;

import com.example.cardealer.repairs.entity.Repair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {

    @Query("select r from Repair r where r.repairDate between ?1 and ?2 or r.car.bodyNumber = ?3 or r.repairAmount = ?4")
    List<Repair> findRepairFromSearchButton(LocalDate from, LocalDate to, String vinNumber, BigDecimal repairAmount);

    @Query("select r from Repair r where r.repairDate between ?1 and ?2 or r.car.bodyNumber = ?3 or r.repairAmount = ?4")
    Page<Repair> findRepairFromSearchButton(LocalDate from, LocalDate to, String vinNumber, BigDecimal repairAmount, Pageable pageable);
}
