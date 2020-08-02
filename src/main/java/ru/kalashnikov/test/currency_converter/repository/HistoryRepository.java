package ru.kalashnikov.test.currency_converter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kalashnikov.test.currency_converter.entity.History;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistoryRepository extends CrudRepository<History,Long> {
//    List<History> findAllByDateAndDate(LocalDate startFindDate, LocalDate endFindDate);
    List<History> findByDateBetween(LocalDate startFindDate, LocalDate endFindDate);

}
