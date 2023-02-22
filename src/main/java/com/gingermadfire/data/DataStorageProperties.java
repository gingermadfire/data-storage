package com.gingermadfire.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("data-storage")
public class DataStorageProperties {

    private String uploadPath;

}
