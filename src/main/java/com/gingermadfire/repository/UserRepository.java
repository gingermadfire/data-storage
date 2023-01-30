package com.gingermadfire.repository;

import com.gingermadfire.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {



    boolean findUserStatus(long id);

    void changeUserStatusById(boolean status, long id);

    void changePasswordById(String password, long id);

    void changeEmailById(String email, long id);
}
