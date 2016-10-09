package morgan.learn.eureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * Created by morgan on 16/10/8.
 */
@RestController
public class Controller {

    @Autowired
    DiscoveryClient discoveryClient;

    @RequestMapping("/sentence")
    String sentence() {
        return getWord("eureka-client-test1") + " , " + getWord("EUREKA-CLIENT-TEST1") ;
    }

    @Autowired
    LoadBalancerClient loadBalancerClient;

    public String getWordFromLB() {
        return null;
    }

    public String getWord(String service) {
        List<ServiceInstance> instances = discoveryClient.getInstances(service);
        if (instances != null && !instances.isEmpty()) {
            URI uri = instances.get(0).getUri();
            if (uri != null) {
                return new RestTemplate().getForObject(uri, String.class);
            }
        }
        return null;
    }
}
