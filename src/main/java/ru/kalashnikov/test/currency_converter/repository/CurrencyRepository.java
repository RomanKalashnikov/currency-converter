package ru.kalashnikov.test.currency_converter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kalashnikov.test.currency_converter.entity.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency,Long> {
}

