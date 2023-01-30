package com.gingermadfire.repository;

import com.gingermadfire.persistence.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<UserFile, Long> {

    void deleteUserFilesById(long id);

    UserFile findUserFileById(UserFile userFile, long id);

    void deleteUserFileById(UserFile userFile, long id);


}
