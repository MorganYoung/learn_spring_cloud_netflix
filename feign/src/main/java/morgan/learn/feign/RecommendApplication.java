package morgan.learn.feign;

import com.netflix.discovery.DiscoveryManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by morgan on 16/10/4.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
@Slf4j
@ComponentScan
public class RecommendApplication {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active","recommend");
        SpringApplication.run(RecommendApplication.class, args);
        log.info("Register ShutdownHook");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log.info("Shutting down product service, unregister from Eureka!");
                DiscoveryManager.getInstance().shutdownComponent();
            }
        });

    }
}
