package com.example.cardealer.model.dtos;

import com.example.cardealer.model.Car;
import com.example.cardealer.model.Customer;
import com.example.cardealer.model.Invoice;
import com.example.cardealer.model.enums.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AgreementDto {
    private Integer id;
    private String content;
    private Customer customer;
    private Car car;
    private Transaction transaction;
    private List<Invoice> invoiceList;
    private String carMark;
    private String carModel;
    private String carBodyNumber;
    private String customerFirstName;
    private String customerLastName;
}
