package morgan.learn.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by morgan on 16/10/4.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaSentenceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaSentenceApplication.class, args);
    }
}
