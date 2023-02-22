package com.gingermadfire.repository;

import com.gingermadfire.persistence.Role;
import com.gingermadfire.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u.role from User u where u.id = :id")
    Role findUserRole(@Param("id") long id);
}
