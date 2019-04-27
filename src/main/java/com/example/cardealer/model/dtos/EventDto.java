
package com.example.cardealer.model.dtos;

import com.example.cardealer.model.Car;
import com.example.cardealer.model.Customer;
import com.example.cardealer.model.Worker;
import com.example.cardealer.model.enums.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDto {

    private Integer id;
    private Transaction transaction;
    private Customer customer;
    private Car car;
    private String eventDate;
    private Worker worker;
}
