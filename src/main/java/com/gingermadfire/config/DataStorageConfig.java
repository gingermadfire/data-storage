package com.gingermadfire.config;

import com.gingermadfire.data.DataStorageProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DataStorageProperties.class)
public class DataStorageConfig {

}
