package com.gingermadfire.repository;

import com.gingermadfire.persistence.Role;
import com.gingermadfire.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u.role from User u where u.id = :id")
    Role findUserRole(@Param("id") long id);

    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);

    @Query("select u.password from User u where u.id = :id")
    String findUserPasswordById(Long id);
}
