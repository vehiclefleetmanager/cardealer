package com.example.cardealer.documents.entiity;

import com.example.cardealer.utils.enums.Transaction;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * This class describes the Agreements in the application.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "agreements")
public class Agreement extends Document {

    private String agreementNumber;

    private String content;

    @OneToOne
    private Invoice invoice;


    public Agreement(LocalDate date, String content, Transaction transaction) {
        setCreatedAt(date);
        setTransaction(transaction);
        this.content = content;
    }
}