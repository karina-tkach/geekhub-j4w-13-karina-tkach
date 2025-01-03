package org.geekhub.encryption.users;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> getUser(int userId);

    default boolean isUserExists(int userId) {
        return getUser(userId).isPresent();
    }
    List<User> getAllUsers();
}
