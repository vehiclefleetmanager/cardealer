package com.example.cardealer.repairs.entity;


import com.example.cardealer.entities.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "parts")
public class Part extends BaseEntity {

    private String partName;

    private BigDecimal partAmount;

    private String bodyCarNumber;

    /*@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "repair_id")
    private Repair repair;*/

    public Part(String partName, BigDecimal price, String bodyCarNumber) {
        super();
        this.partName = partName;
        this.partAmount = price;
        this.bodyCarNumber = bodyCarNumber;
    }
}
