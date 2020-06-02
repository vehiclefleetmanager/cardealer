package com.example.cardealer.repairs.entity;


import com.example.cardealer.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "parts")
public class Part extends BaseEntity {

    private String partName;

    private BigDecimal partAmount;

}
