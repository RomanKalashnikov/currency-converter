package ru.kalashnikov.test.currency_converter.mapper;

import org.mapstruct.Mapper;
import ru.kalashnikov.test.currency_converter.dto.HistoryDto;
import ru.kalashnikov.test.currency_converter.entity.History;

import java.util.List;

@Mapper
public interface HistoryMapper {
    History toDomain(HistoryDto dto);

    HistoryDto toDTO(History domain);

    List<History> toDomainList(List<HistoryDto> customDtoList);

    List<HistoryDto> toDTOList(List<History> list);

}
