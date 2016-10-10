package morgan.learn.eureka;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by morgan on 16/10/9.
 */
@org.springframework.cloud.netflix.feign.FeignClient("eureka-client-test")
public interface FeignClient {

    @RequestMapping("/getwords1")
    String getWords1() ;

    @RequestMapping("/getwords2")
    String getWords2();

}
