package com.gingermadfire.service;

import com.gingermadfire.dto.request.UserPasswordChangeRequestDTO;
import com.gingermadfire.dto.request.UserRequestDTO;
import com.gingermadfire.dto.response.UserResponseDto;
import com.gingermadfire.exception.NotFoundException;
import com.gingermadfire.exception.PasswordMismatchException;
import com.gingermadfire.mapper.UserMapper;
import com.gingermadfire.persistence.Role;
import com.gingermadfire.persistence.User;
import com.gingermadfire.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::map)
                .orElseThrow(() -> new NotFoundException(String.format("User by ID %d not found", id)));
    }

    public Page<UserResponseDto> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        List<UserResponseDto> response = users
                .stream()
                .map(userMapper::map)
                .toList();
        return new PageImpl<>(response, users.getPageable(), users.getTotalElements());
    }

    public Role findRole(Long id) {
        return Optional.ofNullable(userRepository.findUserRole(id))
                .orElseThrow(() -> new NotFoundException(String.format("User by ID %d not found", id)));
    }

    @Transactional
    public void save(UserRequestDTO request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User userEntity = userMapper.map(request);
        userEntity.setRole(Role.USER);
        userRepository.save(userEntity);
    }

    @Transactional
    public void delete() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
    }

    public void setRole(Role role, Long id) {
        User user = this.getUser(id);
        user.setRole(role);
        userRepository.save(user);
    }

    public void setPassword(Long id, UserPasswordChangeRequestDTO request) {
        String currentPassword = userRepository.findUserPasswordById(id);

        if (passwordEncoder.matches(request.getPreviousPassword(), currentPassword)) {
            User user = getUser(id);
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new PasswordMismatchException("The entered passwords do not match");
        }
    }

    public void setEmail(Long id, String newEmail) {
        User user = this.getUser(id);
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    public Optional<User> findUser(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUser(Long id) {
        return findUser(id)
                .orElseThrow(() -> new NotFoundException(String.format("User by ID %d not found", id)));
    }

    public User getUser(String username) {
        return findUser(username)
                .orElseThrow(() -> new NotFoundException(String.format("User by username %s not found", username)));
    }

}
