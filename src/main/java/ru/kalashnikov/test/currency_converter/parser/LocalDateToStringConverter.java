package ru.kalashnikov.test.currency_converter.parser;


import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateToStringConverter implements Converter<LocalDateTime, String> {

    @Override
    public String convert(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}