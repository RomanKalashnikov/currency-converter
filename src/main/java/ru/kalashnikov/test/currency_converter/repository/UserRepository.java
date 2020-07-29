package ru.kalashnikov.test.currency_converter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kalashnikov.test.currency_converter.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
}
