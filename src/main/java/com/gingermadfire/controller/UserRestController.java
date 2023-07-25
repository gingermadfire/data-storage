package com.gingermadfire.controller;

import com.gingermadfire.dto.request.RoleRequest;
import com.gingermadfire.dto.request.UserPasswordChangeRequestDTO;
import com.gingermadfire.dto.request.UserRequestDTO;
import com.gingermadfire.dto.response.UserResponseDto;
import com.gingermadfire.persistence.Role;
import com.gingermadfire.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user')")
    public UserResponseDto findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user')")
    public Page<UserResponseDto> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}/role")
    @PreAuthorize("hasAuthority('user')")
    public Role findStatus(@PathVariable Long id) {
        return userService.findRole(id);
    }

    @PostMapping("/register")
    public void save(@RequestBody @Valid UserRequestDTO request) {
        userService.save(request);
    }

    @DeleteMapping
    public void delete() {
        userService.delete();
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasAuthority('admin')")//TODO: оставить permission или сделать с ролями?
    public void setRole(@PathVariable Long id,@RequestBody RoleRequest role) {
        userService.setRole(role.getRole(), id);
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasAuthority('user')")
    public void changePassword(@PathVariable Long id, @RequestBody @Valid UserPasswordChangeRequestDTO request) {
        userService.setPassword(id, request);
    }

    @PutMapping("/{id}/mail")
    @PreAuthorize("hasAuthority('user')")
    public void changeEmail(@PathVariable Long id, String newEmail) {
        userService.setEmail(id, newEmail);
    }

}
