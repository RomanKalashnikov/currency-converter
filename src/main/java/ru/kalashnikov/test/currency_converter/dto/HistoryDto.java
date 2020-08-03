package ru.kalashnikov.test.currency_converter.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class HistoryDto {
    private Long id;

    private String sourceCurrency;

    private String targetCurrency;

    private Double sourceAmount;
    private Double targetAmount;

    @DateTimeFormat(pattern = "dd.MM.yyyy" , iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate date;
}
