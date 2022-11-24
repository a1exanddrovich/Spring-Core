package com.epam.service;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import com.epam.dao.UserDao;
import com.epam.model.User;

@AllArgsConstructor
public class UserService {

    private UserDao dao;

    public Optional<User> getById(long userId) {
        return dao.findById(userId);
    }

    public Optional<User> getByEmail(String email) {
        return dao.findByEmail(email);
    }

    public List<User> getByName(String name, int pageSize, int pageNumber) {
        return dao.getByName(name, pageSize, pageNumber);
    }

    public User create(User user) {
        return dao.create(user);
    }

    public Optional<User> update(User user) {
        return dao.update(user);
    }

    public boolean delete(long userId) {
        return dao.deleteById(userId);
    }
}
