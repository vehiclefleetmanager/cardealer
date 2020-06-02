package com.example.cardealer.events.control;

import com.example.cardealer.events.boundary.TestDriveRepository;
import com.example.cardealer.events.entity.TestDrive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestDriveService {

    private final TestDriveRepository testDriveRepository;


    public Page<TestDrive> findAllReservations(Pageable pageable) {
        return testDriveRepository.findAll(pageable);
    }
}
