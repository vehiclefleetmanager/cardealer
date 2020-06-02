package com.example.cardealer.documents.boundary;

import com.example.cardealer.documents.entiity.Invoice;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceResponse {
    Long id;
    String invoiceNumber;
    String createdAt;
    String invoiceAmount;
    String transaction;

    public static InvoiceResponse from(Invoice invoice) {
        return new InvoiceResponse(
                invoice.getId(),
                invoice.getInvoiceNumber(),
                invoice.getCreatedAt().toString(),
                invoice.getInvoiceAmount().toString(),
                invoice.getTransaction().name()
        );
    }
}
