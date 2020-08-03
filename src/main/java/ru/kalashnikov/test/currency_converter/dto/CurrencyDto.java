package ru.kalashnikov.test.currency_converter.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CurrencyDto {
    private Long id;

    private String valuteID;

    private Integer numCode;

    private String charCode;

    private Integer nominal;

    private String name;

    private Double value;

    @DateTimeFormat(pattern = "dd.MM.yyyy" , iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate date;
}
