package com.gingermadfire.service;

import com.gingermadfire.persistence.User;
import com.gingermadfire.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean findStatus(long id) {
        return userRepository.findUserStatus(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    public void changeStatus(boolean status, long id) {
        userRepository.changeUserStatusById(status, id);
    }

    public void changePassword(long id, String previousPassword, String newPassword) {
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        String currentPassword = user.getPassword();

        if (currentPassword.equals(previousPassword)) {
            userRepository.changePasswordById(newPassword, id);
        }
    }

    public void changeEmail(long id, String newEmail) {
        userRepository.changeEmailById(newEmail, id);
    }
}
