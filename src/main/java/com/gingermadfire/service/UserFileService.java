package com.gingermadfire.service;

import com.gingermadfire.persistence.UserFile;
import com.gingermadfire.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFileService {

    private final FileRepository fileRepository;

    public UserFile findUserFileById(UserFile userFile,long id) {
        return fileRepository.findUserFileById(userFile, id);
    }

    public List<UserFile> findAll() {
        return fileRepository.findAll();
    }

    public void save(UserFile userFile) {
        fileRepository.save(userFile);
    }

    public void deleteUserFile(UserFile userFile, Long id) {
        fileRepository.deleteUserFileById(userFile, id);
    }

    public void deleteAllFiles(Long id) {
        fileRepository.deleteUserFilesById(id);
    }
}
