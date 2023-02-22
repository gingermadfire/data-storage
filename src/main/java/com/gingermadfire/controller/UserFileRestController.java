package com.gingermadfire.controller;

import com.gingermadfire.data.DataStorageProperties;
import com.gingermadfire.dto.request.FileRequest;
import com.gingermadfire.dto.request.UserRequest;
import com.gingermadfire.dto.response.FileResponse;
import com.gingermadfire.exception.MissingFileExtensionException;
import com.gingermadfire.persistence.UserFile;
import com.gingermadfire.service.UserFileService;
import com.gingermadfire.service.UserService;
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
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-files")
public class UserFileRestController {

    private final UserService userService;
    private final UserFileService userFileService;
    private final DataStorageProperties properties;

    @GetMapping("/{id}")
    public List<FileResponse> findByUserId(@PathVariable long id) {
        return userFileService.findByUserId(id);
    }

    @GetMapping
    public List<FileResponse> findAll() {
        return userFileService.findAll();
    }

    @PostMapping
    public ResponseEntity<UserFile> save(
            @Valid UserRequest userRequest,
            @RequestParam("file")MultipartFile file
            ) throws IOException {
        if (file != null) {
            File uploadDirectory = new File(properties.getUploadPath());

            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            file.transferTo(new File(properties.getUploadPath() + "/" + uuidFile));

            FileRequest fileRequest = new FileRequest();

            //куда проебали имя файла?
            fileRequest.setFileName(uuidFile);
//            fileRequest.setUserId(userRequest.getId());

            int i = Optional.of(Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf('.'))
                    .orElseThrow(() -> new MissingFileExtensionException(String.format("File %s doesn't have extension.", file)));
            String extension = file.getOriginalFilename().substring(i + 1);
            fileRequest.setExtension(extension);

            userFileService.save(fileRequest);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //
    @DeleteMapping("/{file-name}&{id}")
    public ResponseEntity<UserFile> deleteUserFile(
            @Valid @RequestBody @PathVariable("file-name") String fileName,
            @PathVariable("id") long id) {
        userFileService.deleteUserFile(fileName, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Long> deleteAll(@Valid @RequestBody @PathVariable long id) {
        userFileService.deleteAllUserFiles(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
