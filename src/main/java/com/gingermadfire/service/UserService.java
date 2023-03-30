package com.gingermadfire.service;

import com.gingermadfire.dto.request.UserPasswordChangeRequest;
import com.gingermadfire.dto.request.UserRequest;
import com.gingermadfire.dto.response.UserResponse;
import com.gingermadfire.exception.NotFoundException;
import com.gingermadfire.exception.PasswordMismatchException;
import com.gingermadfire.mapper.UserMapper;
import com.gingermadfire.persistence.Role;
import com.gingermadfire.persistence.User;
import com.gingermadfire.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    public UserResponse findById(long id) {
        return userRepository.findById(id)
                .map(userMapper::map)
                .orElseThrow(() -> new NotFoundException(String.format("User by ID %d not found", id)));
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::map)
                .toList();
    }

    public Role findRole(long id) {
        return Optional.ofNullable(userRepository.findUserRole(id))
                .orElseThrow(() -> new NotFoundException(String.format("User by ID %d not found", id)));
    }

    @Transactional
    public void save(UserRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User userEntity = userMapper.map(request);
        userEntity.setRole(Role.USER);
        userRepository.save(userEntity);
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public void setRole(Role role, long id) {
        User user = this.findUser(id);
        user.setRole(role);
        userRepository.save(user);
    }

    public void setPassword(long id, UserPasswordChangeRequest request) {
        User user = this.findUser(id);//TODO: сделать метод который достает только пароль
        String currentPassword = user.getPassword();

        if (passwordEncoder.matches(request.getPreviousPassword(), currentPassword)) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new PasswordMismatchException("The entered passwords do not match");
        }
    }

    public void setEmail(long id, String newEmail) {
        User user = this.findUser(id);
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    protected User findUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User by ID %d not found", id)));
    }
}
