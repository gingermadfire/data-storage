package com.gingermadfire.mapper;

import com.gingermadfire.dto.request.FileRequestDTO;
import com.gingermadfire.dto.response.FileResponseDto;
import com.gingermadfire.dto.response.UserResponseDto;
import com.gingermadfire.persistence.User;
import com.gingermadfire.persistence.UserFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class FileMapper {

    private final UserMapper userMapper;

    public FileResponseDto map(UserFile userFile) {
        FileResponseDto dto = new FileResponseDto();

        dto.setId(userFile.getId());
        dto.setFileName(userFile.getFileName());
        dto.setUserId(userFile.getUser().getId());

        return dto;
    }

    public UserFile map(FileRequestDTO fileRequestDTO, UserResponseDto response) {
        UserFile dto = new UserFile();

        dto.setId(fileRequestDTO.getId());
        dto.setFileName(fileRequestDTO.getFileName());
        dto.setUser(userMapper.map(response));
        dto.setExtension(fileRequestDTO.getExtension());

        return dto;
    }

    public UserFile map(MultipartFile file, User user) {
        UserFile userFile = new UserFile();
        userFile.setFileName(file.getOriginalFilename());
        userFile.setUser(user);
        try {
            String[] split = file.getOriginalFilename().split("\\.");
            String extension = split[1];
            if (StringUtils.isNotBlank(extension)) {
                userFile.setExtension(extension);
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {
        }
        userFile.setUuid(UUID.randomUUID());

        return userFile;
    }
}
