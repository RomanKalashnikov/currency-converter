package ru.kalashnikov.test.currency_converter.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kalashnikov.test.currency_converter.entity.Role;
import ru.kalashnikov.test.currency_converter.entity.User;
import ru.kalashnikov.test.currency_converter.repository.UserRepository;

import java.util.Collections;
import java.util.List;
@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService   {
//public class UserService implements UserDetailsService  {
    @Autowired
    private UserRepository userRepository;

//    public UserDetails loadUserByUsername(String name) {
//        User byName = userRepository.findByName(name);
//        return byName;
//    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        User userFromDB = userRepository.findByName(user.getName());

        if (userFromDB != null) {
            return new User();
        }
        User newUser = new User();
        newUser.setName(newUser.getName());
        newUser.setRoles(Collections.singleton(Role.USER));
        newUser.setPassword(newUser.getPassword());
        userRepository.save(newUser);
        return newUser;
    }



}
