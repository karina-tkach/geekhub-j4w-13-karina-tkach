package org.geekhub.ticketbooking.security;

import lombok.EqualsAndHashCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.geekhub.ticketbooking.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("java:S1948")
@EqualsAndHashCode
public class SecurityUserDetails implements UserDetails {
    private final User user;

    public SecurityUserDetails(User user) {
        this.user = user;
    }

    public int getUserId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(List.of(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
