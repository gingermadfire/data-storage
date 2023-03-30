package com.gingermadfire.controller;

import com.gingermadfire.dto.response.FileResponse;
import com.gingermadfire.persistence.UserFile;
import com.gingermadfire.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileRestController {

    private final FileService fileService;

    @GetMapping()
    public List<FileResponse> findAll() {
        return fileService.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<FileResponse> save(@RequestParam("file") MultipartFile file) {
        fileService.save(file);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @DeleteMapping("/{file-name}&{id}")
    public ResponseEntity<UserFile> deleteUserFile(
            @Valid @RequestBody @PathVariable("file-name") String fileName,
            @PathVariable("id") long id) {
        fileService.deleteUserFile(fileName, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Long> deleteAll(@Valid @RequestBody @PathVariable long id) {
        fileService.deleteAllUserFiles(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
