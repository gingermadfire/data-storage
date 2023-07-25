package com.gingermadfire.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserPasswordChangeRequestDTO {

    @NotBlank
    @Size(min = 6, max = 40)
    private String previousPassword;

    @NotBlank
    @Size(min = 6, max = 40)
    private String newPassword;

}
