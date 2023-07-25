package com.gingermadfire.mapper;

import com.gingermadfire.dto.request.UserRequestDTO;
import com.gingermadfire.dto.response.UserResponseDto;
import com.gingermadfire.persistence.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto map(User user) {
        UserResponseDto dto = new UserResponseDto();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());

        return dto;
    }

    public User map(UserRequestDTO userRequestDTO) {
        User dto = new User();

        dto.setFirstName(userRequestDTO.getFirstName());
        dto.setLastName(userRequestDTO.getLastName());
        dto.setUsername(userRequestDTO.getUsername());
        dto.setEmail(userRequestDTO.getEmail());
        dto.setPassword(userRequestDTO.getPassword());

        return dto;
    }

    public User map(UserResponseDto userResponseDTO) {
        User dto = new User();

        dto.setId(userResponseDTO.getId());
        dto.setFirstName(userResponseDTO.getFirstName());
        dto.setLastName(userResponseDTO.getLastName());

        return dto;
    }

}
