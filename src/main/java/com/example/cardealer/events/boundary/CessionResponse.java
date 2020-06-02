package com.example.cardealer.events.boundary;

import com.example.cardealer.events.entity.Cession;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CessionResponse {
    Long id;
    String createdAt;
    String agreementNumber;
    String customerFirstName;
    String customerLastName;
    String carBodyNumber;
    String transaction;

    public static CessionResponse from(Cession cession) {
        return new CessionResponse(
                cession.getId(),
                cession.getAgreement().getCreatedAt().toString(),
                cession.getAgreement().getAgreementNumber(),
                cession.getCustomer().getFirstName(),
                cession.getCustomer().getLastName(),
                cession.getCar().getBodyNumber(),
                cession.getAgreement().getTransaction().name()
        );
    }
}
