package com.example.cardealer.documents.control;

import com.example.cardealer.documents.boundary.AgreementRepository;
import com.example.cardealer.documents.entiity.Agreement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AgreementService {
    private final AgreementRepository agreementRepository;


    public Page<Agreement> findAllAgreements(Pageable pageable) {
        return agreementRepository.findAllAgreements(pageable);
    }

    public void save(Agreement agreement) {
        agreementRepository.save(agreement);
    }

    public Page<Agreement> findAllAgreementsOfUser(Long userId, Pageable pageable) {
        return agreementRepository.findAllAgreementsOfUser(userId, pageable);
    }
}
