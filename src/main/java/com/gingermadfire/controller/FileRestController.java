package com.gingermadfire.controller;

import com.gingermadfire.dto.response.FileResponseDto;
import com.gingermadfire.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<FileResponseDto> findAll(Pageable pageable) {
        return fileService.findByUserId(pageable);
    }

    @PostMapping("/save")
    public ResponseEntity<FileResponseDto> save(@RequestParam("file") MultipartFile file) {
        fileService.save(file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<Void> deleteUserFile(@PathVariable List<Long> ids) {
        fileService.delete(ids);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
