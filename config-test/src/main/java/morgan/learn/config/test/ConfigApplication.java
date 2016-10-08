package morgan.learn.config.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by morgan on 16/10/4.
 */
@SpringBootApplication
@EnableConfigServer
//@EnableEurekaClient
public class ConfigApplication {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active","server");
        SpringApplication.run(ConfigApplication.class, args);
    }
}
