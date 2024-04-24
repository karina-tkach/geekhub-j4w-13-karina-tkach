package org.geekhub.ticketbooking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize ->
                authorize.requestMatchers("/register", "/main", "/users/register",
                        "/reset-password", "/password-request").permitAll()
                    .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                    .requestMatchers("/admin/**", "/admin/*").hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                    .anyRequest().authenticated()
            )
            .formLogin(
                form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .permitAll()
                    .defaultSuccessUrl("/main")
            ).logout(
                logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .permitAll()
                    .logoutSuccessUrl("/login?logout").permitAll()
                    .deleteCookies("JSESSIONID")
            )
            .rememberMe(rememberMe -> rememberMe
                .key("uniqueAndSecret")
                .tokenValiditySeconds(604800)
            )
            .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth,
                                        UserDetailsService userDetailsService) throws Exception {

        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }
}
