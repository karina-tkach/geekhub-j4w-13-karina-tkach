package org.geekhub.ticketbooking.validator;

import org.geekhub.ticketbooking.exception.UserValidationException;
import org.geekhub.ticketbooking.model.User;
import org.geekhub.ticketbooking.model.Role;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@SuppressWarnings("java:S1192")
public class UserValidator {
    public void validate(User user) {
        validateUserIsNotNull(user);
        validateName(user.getFirstName(), "firstName");
        validateName(user.getLastName(), "lastName");
        validatePassword(user.getPassword());
        validateEmail(user.getEmail());
        validateRole(user.getRole());
    }

    public void validateUserToDelete(User user) {
        validateUserIsNotNull(user);
        validateRole(user.getRole());
    }

    public void validateUsersForUpdate(User userToUpdate, User user) {
        validateUserIsNotNull(userToUpdate);

        User userWithoutRole = new User(user);
        userWithoutRole.setRole(Role.USER);

        if (user.getPassword() != null) {
            validate(userWithoutRole);
        } else {
            throw new UserValidationException("Password can't be null");
        }

        if (userToUpdate.getRole() == Role.SUPER_ADMIN
            && user.getRole() != Role.SUPER_ADMIN) {
            throw new UserValidationException(
                "Can't update role of user with role 'SUPER_ADMIN'"
            );
        }

        if (user.getRole() == Role.SUPER_ADMIN
            && userToUpdate.getRole() != Role.SUPER_ADMIN) {
            throw new UserValidationException(
                "Can't update role of user to role 'SUPER_ADMIN'"
            );
        }
    }

    private void validateUserIsNotNull(User user) {
        if (user == null) {
            throw new UserValidationException("User was null");
        }
    }

    private void validateRole(Role role) {
        if (role == null) {
            throw new UserValidationException("User role was null");
        }
        if (role == Role.SUPER_ADMIN) {
            throw new UserValidationException("User has role 'SUPER_ADMIN'");
        }
    }

    private void validateName(String name, String propertyName) {
        if (name == null) {
            throw new UserValidationException("User " + propertyName + " was null");
        }
        if (name.isBlank()) {
            throw new UserValidationException("User " + propertyName + " was empty");
        }
        if (name.length() > 100 || name.length() < 2) {
            throw new UserValidationException("User " + propertyName + " had wrong length");
        }
        Pattern pattern = Pattern.compile("^([A-Za-z-]){2,100}$");
        if (!pattern.matcher(name).find()) {
            throw new UserValidationException("User name had invalid characters");
        }
    }

    private void validateEmail(String email) {
        if (email == null) {
            throw new UserValidationException("User email was null");
        }
        if (email.isBlank()) {
            throw new UserValidationException("User email was empty");
        }
        validateEmailHasProperStructure(email);
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new UserValidationException("User password was null");
        }
        if (password.isBlank()) {
            throw new UserValidationException("User password was empty");
        }
        if (!validateSymbols(password)) {
            throw new UserValidationException("User password didn't match password pattern");
        }
        if (password.length() > 20 || password.length() < 8) {
            throw new UserValidationException("User password had wrong length");
        }
    }

    private boolean validateSymbols(String str) {
        int upperCase = 0;
        int lowerCase = 0;
        int digits = 0;
        for (char symbol : str.toCharArray()) {
            if (symbol >= 'A' && symbol <= 'Z') {
                upperCase++;
            } else if (symbol >= 'a' && symbol <= 'z') {
                lowerCase++;
            } else if (symbol >= '0' && symbol <= '9') {
                digits++;
            }
        }
        return upperCase != 0
            && lowerCase != 0
            && digits != 0;
    }

    private void validateEmailHasProperStructure(String email) {
        Pattern characters = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        if (!characters.matcher(email).find()) {
            throw new UserValidationException("User email was invalid");
        }
    }
}
