package com.gingermadfire.mapper;

import com.gingermadfire.dto.request.FileRequest;
import com.gingermadfire.dto.response.FileResponse;
import com.gingermadfire.dto.response.UserResponse;
import com.gingermadfire.persistence.UserFile;
import com.gingermadfire.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FileMapper {

    private final UserService userService;
    private final UserMapper userMapper;

    public FileResponse map(UserFile userFile) {
        FileResponse dto = new FileResponse();

        dto.setId(userFile.getId());
        dto.setFileName(userFile.getFileName());
        dto.setUserId(dto.getUserId());

        return dto;
    }

    public UserFile map(FileRequest fileRequest, UserResponse response) {
        UserFile dto = new UserFile();

        dto.setId(fileRequest.getId());
        dto.setFileName(fileRequest.getFileName());
        dto.setUser(userMapper.map(response));
        dto.setExtension(fileRequest.getExtension());

        return dto;
    }
}
