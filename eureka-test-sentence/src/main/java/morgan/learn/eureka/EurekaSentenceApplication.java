package morgan.learn.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by morgan on 16/10/4.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class EurekaSentenceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaSentenceApplication.class, args);
    }
}
