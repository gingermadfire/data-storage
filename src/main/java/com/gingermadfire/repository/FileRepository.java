package com.gingermadfire.repository;

import com.gingermadfire.persistence.User;
import com.gingermadfire.persistence.UserFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<UserFile, Long> {

    Page<UserFile> findByUser(User user, Pageable pageable);

    void deleteById(Long id);

    List<UserFile> findAllByIdInAndUserId(List<Long> ids, Long userId);

}
