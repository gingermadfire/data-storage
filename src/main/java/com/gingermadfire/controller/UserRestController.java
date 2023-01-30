package com.gingermadfire.controller;

import com.gingermadfire.persistence.User;
import com.gingermadfire.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public User findById(long id) {
        return userService.findById(id);
    }

    public List<User> findAll() {
        return userService.findAll();
    }

    public boolean findStatus(long id) {
        return userService.findStatus(id);
    }

    @PostMapping
    public void save(User user) {
        userService.save(user);
    }

    @DeleteMapping
    public void deleteById(long id) {
        userService.deleteById(id);
    }

    public void changeStatus(boolean status, long id) {
        userService.changeStatus(status, id);
    }

    public void changePassword(long id, String previousPassword, String newPassword) {
        userService.changePassword(id, previousPassword, newPassword);
    }

    public void changeEmail(long id, String newEmail) {
        userService.changeEmail(id, newEmail);
    }

}
