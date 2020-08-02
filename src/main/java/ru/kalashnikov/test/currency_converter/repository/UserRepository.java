package ru.kalashnikov.test.currency_converter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kalashnikov.test.currency_converter.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
