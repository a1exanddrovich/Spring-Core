package com.epam.dao;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.annotation.BindStaticData;
import com.epam.entity.UserImpl;
import com.epam.model.User;
import com.epam.util.IdGenerator;
import com.epam.util.Paginator;

@Slf4j
@Setter
public class UserDao {

    @BindStaticData(fileLocation = "preparedUsers.json", castTo = UserImpl.class)
    private final Map<Long, User> users = new HashMap<>();
    private Paginator<User> paginator;
    private IdGenerator generator;

    public Optional<User> findById(long userId) {
        LOG.info("Retrieving a user by {} id...", userId);
        return Optional.ofNullable(users.get(userId));
    }

    public User create(User user) {
        user.setId(generator.generateId(UserImpl.class));
        LOG.info("Adding a new user: name - {}, email - {}...", user.getName(), user.getEmail());
        users.put(user.getId(), user);
        LOG.info("The user was added successfully");
        return users.get(user.getId());
    }

    public Optional<User> update(User user) {
        if (users.containsKey(user.getId())) {
            LOG.info("Updating a user by {} id with the following data: name - {}, email - {}...", user.getId(), user.getName(), user.getEmail());
            users.put(user.getId(), user);
            LOG.info("The user was updated successfully");
            return Optional.of(users.get(user.getId()));
        }
        LOG.warn("Such user was not found while updating");
        return Optional.empty();
    }

    public boolean deleteById(long userId) {
        LOG.info("Deleting a user by {} id...", userId);
        if (!users.containsKey(userId)) {
            LOG.warn("No user was found with such id");
            return false;
        }
        users.remove(userId);
        LOG.info("The user was deleted successfully");
        return true;
    }

    public Optional<User> findByEmail(String email) {
        LOG.info("Retrieving a user by {}...", email);
        for (Map.Entry<Long, User> user : users.entrySet()) {
            if (user.getValue().getEmail().equals(email)) {
                return Optional.of(user.getValue());
            }
        }
        LOG.warn("No user was found with such email {}", email);
        return Optional.empty();
    }

    public List<User> getByName(String name, int pageSize, int pageNumber) {
        LOG.info("Retrieving users by {}... Passed page size - {}, page number - {}", name, pageSize, pageNumber);
        return paginator.paginate(this.users
                .values()
                .stream()
                .filter(userEntry -> userEntry.getName().contains(name))
                .collect(Collectors.toList()), pageSize, pageNumber);
    }

}
