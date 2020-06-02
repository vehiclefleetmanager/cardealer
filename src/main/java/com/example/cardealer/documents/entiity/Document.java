package com.example.cardealer.documents.entiity;

import com.example.cardealer.entities.BaseEntity;
import com.example.cardealer.utils.enums.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Document extends BaseEntity {
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private Transaction transaction;
}
