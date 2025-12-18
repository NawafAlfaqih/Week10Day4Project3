package org.example.week10day4project3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.week10day4project3.Model.User;
import org.example.week10day4project3.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllUsers(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(userService.getAllUsers(user.getId()));
    }
}
