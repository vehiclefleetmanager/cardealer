package com.example.cardealer.documents.boundary;

import com.example.cardealer.documents.entiity.Agreement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AgreementResponse {
    private Long id;
    private LocalDate createdAt;
    private String transaction;
    private String agreementNumber;
   /* private String agreementInvoiceNumber;
    private String agreementAmount;*/

    public static AgreementResponse from(Agreement agreement) {
        return new AgreementResponse(
                agreement.getId(),
                agreement.getCreatedAt(),
                agreement.getTransaction().name(),
                agreement.getAgreementNumber()/*,
                agreement.getInvoice().getInvoiceNumber(),
                agreement.getInvoice().getInvoiceAmount().toString()*/
        );
    }
}
