package com.gingermadfire.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileRequest {

    private long id;

    private String fileName;

    private String extension;

    private long userId;

}
