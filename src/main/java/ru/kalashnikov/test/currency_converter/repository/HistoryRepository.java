package ru.kalashnikov.test.currency_converter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kalashnikov.test.currency_converter.entity.History;

@Repository
public interface HistoryRepository extends CrudRepository<History,Long> {
}
