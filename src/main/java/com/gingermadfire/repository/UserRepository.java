package com.gingermadfire.repository;

import com.gingermadfire.persistence.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserRepository {

    void save(User user);

    void delete(long id);

    List<User> findAll();

    User findById(long id);

    boolean getUserStatus(long id);

    void changePassword(long id);

    void changeEmail(long id);
}
