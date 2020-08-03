package ru.kalashnikov.test.currency_converter.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;

    private String name;

    private String password;

    private boolean active;

}
