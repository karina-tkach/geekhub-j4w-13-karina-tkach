package org.geekhub.ticketbooking.security;

import org.geekhub.ticketbooking.user.User;
import org.geekhub.ticketbooking.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new SecurityUserDetails(user);
    }
}
