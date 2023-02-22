package com.gingermadfire.service;

import com.gingermadfire.dto.request.FileRequest;
import com.gingermadfire.dto.response.FileResponse;
import com.gingermadfire.dto.response.UserResponse;
import com.gingermadfire.exception.NotFoundException;
import com.gingermadfire.mapper.FileMapper;
import com.gingermadfire.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFileService {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
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

    public void save(FileRequest fileRequest) {
        UserResponse response = userService.findById(fileRequest.getUserId());
        fileRepository.save(fileMapper.map(fileRequest, response));
    }

    public void deleteUserFile(String fileName, long id) {
        fileRepository.deleteByFileNameAndUserId(fileName, id);
    }

    public void deleteAllUserFiles(Long id) {
        fileRepository.deleteByUserId(id);
    }
}
