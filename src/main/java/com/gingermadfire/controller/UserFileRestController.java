package com.gingermadfire.controller;

import com.gingermadfire.persistence.UserFile;
import com.gingermadfire.service.UserFileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user_files")
public class UserFileRestController {

    private final UserFileService userFileService;

    @Value("${upload.path}")
    private String uploadFilesPath;

    @GetMapping
    public UserFile findById(UserFile userFile, @Valid long id) {
        return userFileService.findUserFileById(userFile, id);
    }

    public List<UserFile> findAll() {
        return userFileService.findAll();
    }

    @PostMapping
    public ResponseEntity<UserFile> save(
            @Valid UserFile userFile,
            @RequestParam("file")MultipartFile file
            ) throws IOException {
        if (file != null) {
            File uploadDirectory = new File(uploadFilesPath);

            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(resultFileName));
            userFile.setFileName(resultFileName);
            userFileService.save(userFile);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<UserFile> deleteUserFile(@Valid @RequestBody UserFile userFile, long id) {
        userFileService.deleteUserFile(userFile, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<Long> deleteAll(@Valid @RequestBody long id) {
        userFileService.deleteAllFiles(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
