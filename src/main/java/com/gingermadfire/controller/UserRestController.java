package com.gingermadfire.controller;

import com.gingermadfire.dto.request.UserRequest;
import com.gingermadfire.dto.response.UserResponse;
import com.gingermadfire.persistence.Role;
import com.gingermadfire.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable long id) {
        return userService.findById(id);
    }

    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}/role")
    public Role findStatus(@PathVariable long id) {
        return userService.findRole(id);
    }

    @PostMapping("/register")
    public void save(@RequestBody UserRequest userRequest) {
        userService.save(userRequest);
    }

    @DeleteMapping
    public void deleteById(long id) {
        userService.deleteById(id);
    }

    public void setRole(Role role, long id) {
        userService.setRole(role, id);
    }

    public void changePassword(long id, String previousPassword, String newPassword) {
        userService.setPassword(id, previousPassword, newPassword);
    }

    public void changeEmail(long id, String newEmail) {
        userService.setEmail(id, newEmail);
    }

}
