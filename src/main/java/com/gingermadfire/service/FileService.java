package com.gingermadfire.service;

import com.gingermadfire.data.DataStorageProperties;
import com.gingermadfire.dto.response.FileResponseDto;
import com.gingermadfire.exception.FileException;
import com.gingermadfire.mapper.FileMapper;
import com.gingermadfire.persistence.Role;
import com.gingermadfire.persistence.User;
import com.gingermadfire.persistence.UserFile;
import com.gingermadfire.repository.FileRepository;
import com.gingermadfire.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final DataStorageProperties properties;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final UserService userService;

    public Page<FileResponseDto> findByUserId(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUser(username);
        Page<UserFile> userFiles = fileRepository.findByUser(user, pageable);
        List<FileResponseDto> fileResponseDtos = userFiles
                .stream()
                .map(fileMapper::map)
                .toList();
        return new PageImpl<>(fileResponseDtos, userFiles.getPageable(), userFiles.getTotalElements());
    }

    public void save(MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        User user = userService.getUser(securityUser.getId());

        if (user.getRole() == Role.USER && file.getSize() > 10485760L) {
            throw new FileException("Размер файла превышает 10МБ");
        }

        UserFile userFile = fileMapper.map(file, user);

        File uploadDirectory = new File(properties.getUploadPath());

        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdir();
        }

        try {
            file.transferTo(new File(properties.getUploadPath() + "/" + userFile.getUuid().toString()
                    + "." + userFile.getExtension()));
            fileRepository.save(userFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    public void delete(List<Long> ids) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        List<UserFile> files = fileRepository.findAllByIdInAndUserId(ids, securityUser.getId());
        fileRepository.deleteAllById(ids);

        for (UserFile file : files) {
            try {
                Files.delete(Path.of(properties.getUploadPath() + "/" + file.getUuid() + "." + file.getExtension()));
            } catch (IOException e) {
                throw new FileException("Не удалось удалить файл по id: %d".formatted(file.getId()));
            }
        }
    }

}
