package com.gingermadfire;

import com.gingermadfire.data.DataStorageProperties;
import com.gingermadfire.persistence.Role;
import com.gingermadfire.persistence.User;
import com.gingermadfire.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DataStorageProperties.class)
public class App implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.findById(1L)
                .orElseGet(() -> {
                    User user = new User();

                    user.setFirstName("Maxim");
                    user.setLastName("Khusnullin");
                    user.setRole(Role.ADMIN);
                    user.setEmail("Maxim.ilian98@yandex.ru");

                    return userRepository.save(user);
                });
    }
}
