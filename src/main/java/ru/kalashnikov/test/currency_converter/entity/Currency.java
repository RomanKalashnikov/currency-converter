package ru.kalashnikov.test.currency_converter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String valuteID;

    private Integer numCode;

    private String charCode;

    private Integer nominal;

    private String name;

    private Double value;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

}
