package com.example.cardealer.documents.boundary;

import com.example.cardealer.documents.entiity.Agreement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {

    @Query("select a from Agreement a")
    Page<Agreement> findAllAgreements(Pageable pageable);

    @Query("select a from Agreement a where a.customer.id = ?1")
    Page<Agreement> findAllAgreementsOfUser(Long userId, Pageable pageable);

    @Query("select a from Agreement a where a.customer.id =?1")
    List<Agreement> findAllAgreementsOfCustomer(Long id);
}
