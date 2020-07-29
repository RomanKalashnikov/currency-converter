package ru.kalashnikov.test.currency_converter.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sourceCurrency;

    private String targetCurrency;

    private Long sourceAmount;

    private Long targetAmount;

    private LocalDate date;

}
