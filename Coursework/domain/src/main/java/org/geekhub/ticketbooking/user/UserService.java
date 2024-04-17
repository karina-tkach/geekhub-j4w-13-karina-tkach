package org.geekhub.ticketbooking.user;

import jakarta.mail.MessagingException;
import org.geekhub.ticketbooking.exception.UserValidationException;
import org.geekhub.ticketbooking.mail.MailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private final UserValidator validator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserValidator userValidator;

    public UserService(UserValidator validator, UserRepository userRepository,
                       UserValidator userValidator, MailSenderService mailSenderService) {
        this.validator = validator;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(12);
        this.userValidator = userValidator;
        this.mailSenderService = mailSenderService;
    }

    public User addUser(User user) {
        try {
            logger.info("Try to add user");
            userValidator.validate(user);

            User existsUser = getUserByEmail(user.getEmail());
            if (existsUser != null) {
                throw new UserValidationException(
                    "User with email '" + existsUser.getEmail() + "' already exists"
                );
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            int id = userRepository.addUser(user);
            if (id == -1) {
                throw new UserValidationException("Unable to retrieve the generated key");
            }

            user.setId(id);
            logger.info("User was added:\n{}", user);
            return getUserById(id);
        } catch (UserValidationException | DataAccessException exception) {
            logger.warn("User wasn't added: {}\n{}", user, exception.getMessage());
            return null;
        }
    }

    public User getUserById(int id) {
        try {
            logger.info("Try to get user by id");
            User user = userRepository.getUserById(id);
            logger.info("User was fetched successfully");
            return user;
        } catch (DataAccessException exception) {
            logger.warn("User wasn't fetched\n{}", exception.getMessage());
            return null;
        }
    }

    public User getUserByEmail(String email) {
        try {
            logger.info("Try to get user by email");
            User user = userRepository.getUserByEmail(email);
            logger.info("User was fetched successfully");
            return user;
        } catch (DataAccessException exception) {
            logger.warn("User wasn't fetched\n{}", exception.getMessage());
            return null;
        }
    }

    public boolean deleteUserById(int id) {
        User userToDel = getUserById(id);
        try {
            logger.info("Try to delete user");
            validator.validateUserToDelete(userToDel);

            userRepository.deleteUserById(id);
            logger.info("User was deleted:\n{}", userToDel);
            return true;
        } catch (UserValidationException | DataAccessException exception) {
            logger.warn("User wasn't deleted: {}\n{}", userToDel, exception.getMessage());
            return false;
        }
    }

    public User updateUserById(User user, int id) {
        User userToUpdate = getUserById(id);
        try {
            logger.info("Try to update user");
            validator.validateUsersForUpdate(userToUpdate, user);

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.updateUserById(user, id);

            logger.info("User was updated:\n{}", user);
            return getUserById(id);
        } catch (UserValidationException | DataAccessException exception) {
            logger.warn("User wasn't updated: {}\n{}", id, exception.getMessage());
            return null;
        }
    }

    public User updateUserWithoutPasswordChangeById(User user, int id) {
        User userToUpdate = getUserById(id);
        try {
            logger.info("Try to update user without password change");

            validator.validateUsersForUpdate(userToUpdate, user);

            userRepository.updateUserWithoutPasswordChangeById(user, id);

            logger.info("User was updated without password change:\n{}", user);
            return getUserById(id);
        } catch (UserValidationException | DataAccessException exception) {
            logger.warn("User wasn't updated without password change: {}\n{}", id, exception.getMessage());
            return null;
        }
    }

    public List<User> getUsersWithPagination(int pageNumber, int limit) {
        try {
            if (pageNumber < 0 || limit < 0) {
                throw new IllegalArgumentException("Page number and limit must be greater than 0");
            }
            logger.info("Try to get user with pagination");
            List<User> users = userRepository.getUsersWithPagination(pageNumber, limit);
            logger.info("Users were fetched with pagination successfully");
            return users;
        } catch (IllegalArgumentException | DataAccessException exception) {
            logger.warn("Users weren't fetched with pagination\n{}", exception.getMessage());
            return Collections.emptyList();
        }
    }

    public int getUsersRowsCount() {
        try {
            logger.info("Try to get user rows count");
            int count = userRepository.getUsersRowsCount();
            logger.info("Users rows count were fetched successfully");
            return count;
        } catch (DataAccessException | NullPointerException exception) {
            logger.warn("Users rows count weren't fetched\n{}", exception.getMessage());
            return -1;
        }
    }

    public boolean sendPasswordResetEmail(String password, String email) {
        String mail = this.getPasswordResetEmailContent(password);
        try {
            mailSenderService.sendEmail(email, "Password Change By Admin", mail);
            return true;
        } catch (MailException | MessagingException | UnsupportedEncodingException e) {
            return false;
        }
    }

    public String getPasswordResetEmailContent(String password) {
        return "<p>Hello!</p>"
            + "Your password was changed by administrator"
            + "<br>"
            + "<p>Your new password: " + password + "</p>"
            + "<br>"
            + "You can always reset it!";
    }
}
