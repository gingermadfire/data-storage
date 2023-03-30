package com.gingermadfire.controller;

import com.gingermadfire.dto.request.UserPasswordChangeRequest;
import com.gingermadfire.dto.request.UserRequest;
import com.gingermadfire.dto.response.UserResponse;
import com.gingermadfire.persistence.Role;
import com.gingermadfire.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('users:read')")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('users:read')")
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}/role")
    @PreAuthorize("hasAuthority('users:read')")
    public Role findStatus(@PathVariable Long id) {
        return userService.findRole(id);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('users:write')")
    public void save(@RequestBody @Valid UserRequest request) {
        userService.save(request);
    }

    @DeleteMapping("{id}/delete")
    @PreAuthorize("hasAuthority('users:delete')")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PutMapping("{id}/role")
    @PreAuthorize("hasAuthority('users:put')")
    public void setRole(@PathVariable Long id,@RequestBody Role role) {
        userService.setRole(role, id);
    } //TODO

    @PutMapping("/{id}/password")
    @PreAuthorize("hasAuthority('users:put')")
    public void changePassword(@PathVariable Long id, @RequestBody @Valid UserPasswordChangeRequest request) {
        userService.setPassword(id, request);
    }

    @PutMapping("/{id}/mail")
    @PreAuthorize("hasAuthority('users:put')")
    public void changeEmail(@PathVariable Long id, String newEmail) {
        userService.setEmail(id, newEmail);
    }

}
