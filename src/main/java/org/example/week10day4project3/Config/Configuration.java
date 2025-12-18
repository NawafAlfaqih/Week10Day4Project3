package org.example.week10day4project3.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1//user/get").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/customer/get").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/customer/add").permitAll()
                        .requestMatchers("/api/v1/customer/update").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/v1/customer/delete").hasAuthority("CUSTOMER")
                        .requestMatchers("api/v1/employee/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/account/get").hasAuthority("ADMIN")
                        .requestMatchers("api/v1/account/create").hasAuthority("CUSTOMER")
                        .requestMatchers("api/v1/account/update/{accountId}").hasAuthority("EMPLOYEE")
                        .requestMatchers("api/v1/account/delete/{accountId}").hasAuthority("CUSTOMER")
                        .requestMatchers("api/v1/account/activate/{accountId}").hasAuthority("EMPLOYEE")
                        .requestMatchers("api/v1/account/block/{accountId}").hasAuthority("EMPLOYEE")
                        .requestMatchers("api/v1/account/get/details/{accountId}").hasAnyAuthority("EMPLOYEE", "ADMIN", "CUSTOMER")
                        .requestMatchers("api/v1/account/get/accounts").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/v1/account/deposit/account-id/{accountId}/amount/{amount}").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/v1/account/withdraw/account-id/{accountId}/amount/{amount}").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/v1/account/transfer/account-id/{accountId}/target/{targetAccountId}/amount/{amount}").hasAuthority("CUSTOMER")
                        .anyRequest().authenticated()
                )

                .logout(logout -> logout
                        .logoutUrl("/api/v1/user/logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                )

                .httpBasic(basic -> {})
                .build();
    }
}
