package com.example.cardealer.repository;

import com.example.cardealer.model.Agreement;
import com.example.cardealer.model.dtos.AgreementDto;
import com.example.cardealer.model.enums.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgreementRepository extends JpaRepository<Agreement, Integer> {

    @Query("select a from Agreement a where a.transaction = ?1")
    List<Agreement> findAgreementByTransaction(Transaction transaction);

}
