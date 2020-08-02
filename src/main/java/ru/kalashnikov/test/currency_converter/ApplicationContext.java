package ru.kalashnikov.test.currency_converter;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.DefaultConversionService;
import ru.kalashnikov.test.currency_converter.parser.LocalDateToStringConverter;
import ru.kalashnikov.test.currency_converter.parser.XmlTolist;

@SpringBootApplication
public class ApplicationContext {
    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(ApplicationContext.class,args);
    }
}
