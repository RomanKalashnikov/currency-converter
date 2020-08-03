package ru.kalashnikov.test.currency_converter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.kalashnikov.test.currency_converter.dto.CurrencyDto;
import ru.kalashnikov.test.currency_converter.entity.Currency;

import java.util.List;

@Mapper
public interface CurrencyMapper {
    Currency toDomain(CurrencyDto dto);

    CurrencyDto toDTO(Currency domain);

    List<Currency> toDomainList(List<CurrencyDto> customDtoList);

    List<CurrencyDto> toDTOList(List<Currency> list);
    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.valuteID", target = "valuteID")
    @Mapping(source = "dto.numCode", target = "numCode")
    @Mapping(source = "dto.charCode", target = "charCode")
    @Mapping(source = "dto.nominal", target = "nominal")
    @Mapping(source = "dto.name", target = "name")
    @Mapping(source = "dto.value", target = "value")
    @Mapping(source = "dto.date", target = "date")
    Currency merge (Currency domain, CurrencyDto dto);
}
