package org.example.week10day4project3.Service;

import lombok.RequiredArgsConstructor;
import org.example.week10day4project3.Api.ApiException;
import org.example.week10day4project3.Model.User;
import org.example.week10day4project3.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers(Integer adminId) {
        User user = userRepository.findUserById(adminId);
        if (user == null)
            throw new ApiException("user not found");

        if (!"ADMIN".equals(user.getRole()))
            throw new ApiException("UnAuthorized, Not Admin");

        return userRepository.findAll();
    }
}
