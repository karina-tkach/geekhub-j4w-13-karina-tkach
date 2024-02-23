package org.geekhub.encryption;

import org.geekhub.encryption.users.User;
import org.geekhub.encryption.users.UserRepository;
import org.geekhub.encryption.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        this.userService = new UserService(userRepository);
    }

    @Test
    void getAllUsers_shouldReturnAllUsers_always() {
        List<User> users = new ArrayList<>();
        users.add(new User(1,"John","john@email.com"));
        users.add(new User(2,"Anna","anna@email.com"));
        users.add(new User(3,"Emma","emma@email.com"));

        when(userRepository.getAllUsers()).thenReturn(users);

        List<User> actualUsers = userService.getAllUsers();

        assertEquals(users, actualUsers, "getAllUsers method is incorrect.");
    }

    @Test
    void getUser_shouldReturnUser_whenPresent() {
        User expectedUser = new User(1,"John","john@email.com");

        when(userRepository.getUser(1)).thenReturn(Optional.of(expectedUser));

        User user = userService.getUser(1);

        assertEquals(expectedUser, user, "getUser method is incorrect.");
    }

    @Test
    void getUser_shouldThrowException_whenAbsent() {
        when(userRepository.getUser(1)).thenReturn(Optional.empty());

        assertThatCode(() -> userService.getUser(1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("User with id 1 not found");
    }

    @Test
    void isUserExist_shouldReturnProperValue_always() {
        User expectedUser = new User(1,"John","john@email.com");

        when(userRepository.getUser(1)).thenReturn(Optional.of(expectedUser));
        when(userRepository.getUser(2)).thenReturn(Optional.empty());
        when(userRepository.isUserExists(1)).thenCallRealMethod();
        when(userRepository.isUserExists(2)).thenCallRealMethod();

        boolean isFirstUserExist = userService.isUserExist(1);
        boolean isSecondUserExist = userService.isUserExist(2);

        assertTrue(isFirstUserExist, "isUserExist method is incorrect");
        assertFalse(isSecondUserExist, "isUserExist method is incorrect");
    }
}
