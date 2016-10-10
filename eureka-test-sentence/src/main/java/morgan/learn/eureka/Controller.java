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
        return getWord("eureka-client-test") + " , " + getWord("EUREKA-CLIENT-TEST") ;
    }

    @RequestMapping("/sentencelb")
    String sentencelb() {
        return getWordFromLB("eureka-client-test") + "," + getWordFromLB("eureka-client-test");
    }

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    FeignClient feignClient;

    @RequestMapping("/senfeign")
    String senfeign() {
        return feignClient.getWords1() + "," + feignClient.getWords2();
    }

    public String getWordFromLB(String service) {
        ServiceInstance choose = loadBalancerClient.choose(service);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(choose.getUri().toString()+"getwords1", String.class);
//        return new RestTemplate().getForObject(choose.getUri(), String.class);
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
