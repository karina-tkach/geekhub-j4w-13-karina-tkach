package org.geekhub.encryption.repository;

import org.geekhub.encryption.models.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> getUser(int userId);

    default boolean isUserExists(int userId) {
        return getUser(userId).isPresent();
    }
}
