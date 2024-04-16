package org.geekhub.ticketbooking.repository.interfaces;

import org.geekhub.ticketbooking.model.User;

import java.util.List;

public interface UserRepository {
    int addUser(User user);

    User getUserById(int id);

    User getUserByEmail(String email);

    void deleteUserById(int id);

    void updateUserById(User user, int id);

    void updateUserWithoutPasswordChangeById(User user, int id);

    List<User> getUsersWithPagination(int pageNumber, int limit);

    int getUsersRowsCount();
}
