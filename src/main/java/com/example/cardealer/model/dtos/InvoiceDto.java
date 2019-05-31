package com.example.cardealer.model.dtos;

import com.example.cardealer.model.Agreement;
import com.example.cardealer.model.Worker;
import com.example.cardealer.model.enums.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class InvoiceDto {
    private Integer id;
    private String invoiceNumber;
    private BigDecimal price;
    private String placeOfIssue;
    private Date dateOfIssue;
    private Transaction transaction;
    private Agreement agreement;
    private Worker worker;
}
