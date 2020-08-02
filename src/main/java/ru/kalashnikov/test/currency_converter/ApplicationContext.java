package ru.kalashnikov.test.currency_converter;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationContext {
    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(ApplicationContext.class,args);

    }
}
