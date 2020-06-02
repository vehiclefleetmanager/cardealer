package com.example.cardealer.events.boundary;

import com.example.cardealer.events.entity.Cession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CessionRepository extends JpaRepository<Cession, Long> {
    @Query("select c from Cession c where c.agreement.transaction = 'CESSION'")
    List<Cession> findAllCessions();

    @Query("select c from Cession c where c.agreement.transaction = 'CESSION'")
    Page<Cession> findAllCessions(Pageable pageable);

}
