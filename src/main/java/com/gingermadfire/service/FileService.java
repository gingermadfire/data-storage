package com.gingermadfire.service;

import com.gingermadfire.data.DataStorageProperties;
import com.gingermadfire.dto.response.FileResponse;
import com.gingermadfire.exception.NotFoundException;
import com.gingermadfire.mapper.FileMapper;
import com.gingermadfire.persistence.UserFile;
import com.gingermadfire.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final DataStorageProperties properties;
    private final UserService userService;

    public List<FileResponse> findByUserId(long id) {
        return Optional.of(fileRepository.findByUserId(id)
                .stream()
                .map(fileMapper::map)
                .toList())
                .orElseThrow(() -> new NotFoundException(String.format("User by ID %d not found", id)));
    }

    public List<FileResponse> findAll() {
        return fileRepository.findAll()
                .stream()
                .map(fileMapper::map)
                .toList();
    }

    public void save(MultipartFile file) {
        UserFile userFile = new UserFile();

        userFile.setFileName(file.getOriginalFilename());
        userFile.setUser(userService.findUser(1L));
        String[] split = file.getOriginalFilename().split("\\.");
        userFile.setExtension(split[1]); //todo
        userFile.setUuid(UUID.randomUUID());
        File uploadDirectory = new File(properties.getUploadPath());

        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdir();
        }

        try {
            file.transferTo(new File(properties.getUploadPath() + "/" + file.getOriginalFilename()));
            fileRepository.save(userFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteUserFile(String fileName, long id) {
        fileRepository.deleteByFileNameAndUserId(fileName, id);
    }

    public void deleteAllUserFiles(Long id) {
        fileRepository.deleteByUserId(id);
    }
}
