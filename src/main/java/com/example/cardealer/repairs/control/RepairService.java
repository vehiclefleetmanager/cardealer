package com.example.cardealer.repairs.control;

import com.example.cardealer.repairs.boundary.RepairRepository;
import com.example.cardealer.repairs.entity.Repair;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepairService {
    private final RepairRepository repairRepository;


    public Page<Repair> findAllRepairs(Pageable pageable) {
        return repairRepository.findAll(pageable);
    }

    public Repair getRepairById(Long id) {
        return repairRepository.getOne(id);
    }

    public List<Repair> getRepairsFormSearchButton(String from, String to, String vinNumber, String repairAmount) {
        LocalDate fromDate = null;
        LocalDate toDate = null;
        BigDecimal amountValue = BigDecimal.ZERO;
        if (!from.isEmpty()) {
            fromDate = checkAndGetDataValue(from);
        }
        if (!to.isEmpty()) {
            toDate = checkAndGetDataValue(to);
        }
        if (!repairAmount.isEmpty()) {
            amountValue = checkAndGetAmountValue(repairAmount);
        }
        return repairRepository.findRepairFromSearchButton(fromDate, toDate, vinNumber, amountValue);
    }

    public Page<Repair> getRepairsFormSearchButton(String from, String to, String vinNumber, String repairAmount, Pageable pageable) {
        LocalDate fromDate = null;
        LocalDate toDate = null;
        BigDecimal amountValue = BigDecimal.ZERO;
        if (!from.isEmpty()) {
            fromDate = checkAndGetDataValue(from);
        }
        if (!to.isEmpty()) {
            toDate = checkAndGetDataValue(to);
        }
        if (!repairAmount.isEmpty()) {
            amountValue = checkAndGetAmountValue(repairAmount);
        }
        return repairRepository.findRepairFromSearchButton(fromDate, toDate, vinNumber, amountValue, pageable);
    }

    private BigDecimal checkAndGetAmountValue(String repairAmount) {
        return BigDecimal.valueOf(Long.parseLong(repairAmount));
    }

    private LocalDate checkAndGetDataValue(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}
