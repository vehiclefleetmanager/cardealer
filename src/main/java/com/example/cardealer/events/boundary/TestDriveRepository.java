package com.example.cardealer.events.boundary;

import com.example.cardealer.events.entity.TestDrive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestDriveRepository extends JpaRepository<TestDrive, Long> {

    @Query("select t from TestDrive t where t.user.id = ?1")
    Page<TestDrive> findAllReservationsOfUser(Long id, Pageable pageable);
}
