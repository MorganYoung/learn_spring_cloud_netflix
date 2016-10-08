package morgan.learn.config.test.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Created by morgan on 16/10/8.
 */
@SpringBootApplication
@EnableConfigurationProperties
public class ConfigClientApplication {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active","client");
        SpringApplication.run(ConfigClientApplication.class, args);
    }
}
