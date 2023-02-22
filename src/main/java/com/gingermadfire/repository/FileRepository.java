package com.gingermadfire.repository;

import com.gingermadfire.persistence.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<UserFile, Long> {

    void deleteByUserId(long id);

    @Query("select f from UserFile f where f.id = :id")
    List<UserFile> findByUserId(@Param("id") long id);

    void deleteByFileNameAndUserId(String fileName, long userId);
}
