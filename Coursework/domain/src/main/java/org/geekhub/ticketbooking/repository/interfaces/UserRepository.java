package org.geekhub.ticketbooking.repository.interfaces;

import org.geekhub.ticketbooking.model.User;

import java.util.List;

public interface UserRepository {
    List<User> getUsers();

    int addUser(User user);

    User getUserById(int id);

    User getUserByEmail(String email);

    void deleteUserById(int id);

    void updateUserById(User user, int id);
}
