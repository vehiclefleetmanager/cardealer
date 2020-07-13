package com.example.cardealer.repairs.control;

import com.example.cardealer.repairs.boundary.RepairRepository;
import com.example.cardealer.repairs.entity.Repair;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
