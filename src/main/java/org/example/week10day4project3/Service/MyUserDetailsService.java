package org.example.week10day4project3.Service;

import lombok.RequiredArgsConstructor;
import org.example.week10day4project3.Api.ApiException;
import org.example.week10day4project3.Model.User;
import org.example.week10day4project3.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null)
            throw new ApiException("Wrong User or Password ");

        return user;
    }
}
