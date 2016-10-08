package morgan.learn.config.test.client;

import morgan.learn.config.test.client.model.MorganLearn;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by morgan on 16/10/8.
 */
@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan
public class ConfigClientApplication {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active","client");
        SpringApplication.run(ConfigClientApplication.class, args);
    }
}
