package morgan.learn.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by morgan on 16/10/4.
 */
@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
public class EurekaClientTestApplication {
    public static void main(String[] args) {
//        System.setProperty("spring.profile.active","wordscn");
        System.setProperty("spring.profiles.active","wordscn");
        SpringApplication.run(EurekaClientTestApplication.class, args);
    }
}
