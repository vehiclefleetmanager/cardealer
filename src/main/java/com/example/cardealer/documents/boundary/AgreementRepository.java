package com.example.cardealer.documents.boundary;

import com.example.cardealer.documents.entiity.Agreement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {

    @Query("select a from Agreement a where a.transaction = 'PURCHASE' or a.transaction = 'SALE'")
    Page<Agreement> findAllAgreements(Pageable pageable);
}
