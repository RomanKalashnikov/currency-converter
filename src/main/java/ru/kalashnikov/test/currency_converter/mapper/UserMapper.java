package ru.kalashnikov.test.currency_converter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kalashnikov.test.currency_converter.dto.UserDto;
import ru.kalashnikov.test.currency_converter.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    User toDomain(UserDto dto);

    UserDto toDTO(User domain);

    List<User> toDomainList(List<UserDto> customDtoList);

    List<UserDto> toDTOList(List<User> list);
    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.name", target = "name")
    @Mapping(source = "dto.password", target = "password")
    @Mapping(source = "dto.active", target = "active")
    User merge (User domain, UserDto dto);
}
