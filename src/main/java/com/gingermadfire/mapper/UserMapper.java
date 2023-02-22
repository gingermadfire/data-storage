package com.gingermadfire.mapper;

import com.gingermadfire.dto.request.UserRequest;
import com.gingermadfire.dto.response.UserResponse;
import com.gingermadfire.persistence.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse map(User user) {
        UserResponse dto = new UserResponse();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());

        return dto;
    }

    public User map(UserRequest userRequest) {
        User dto = new User();

        dto.setFirstName(userRequest.getFirstName());
        dto.setLastName(userRequest.getLastName());
        dto.setEmail(userRequest.getEmail());
        dto.setPassword(userRequest.getPassword());

        return dto;
    }

    public User map(UserResponse userResponse) {
        User dto = new User();

        dto.setId(userResponse.getId());
        dto.setFirstName(userResponse.getFirstName());
        dto.setLastName(userResponse.getLastName());

        return dto;
    }

}
