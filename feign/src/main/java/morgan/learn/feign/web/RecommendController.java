package morgan.learn.feign.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import morgan.learn.feign.model.Product;
import morgan.learn.feign.model.Recommendation;
import morgan.learn.feign.service.RemoteRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Created by morgan on 16/10/4.
 */
@RestController
@Slf4j
public class RecommendController {
//    @Autowired
//    private SetProcTimeBean setProcTimeBean;

    @Autowired
    RemoteRecommendService remoteRecommendService;

    @RequestMapping("/recommend")
    @HystrixCommand(fallbackMethod = "callRecommendFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
    })
    public List<Recommendation> remoteRecommends(@RequestParam(value = "productId",  required = true) int productId){
        return remoteRecommendService.getRecommendations(productId);
    }

    public List<Recommendation> callRecommendFallback(int productId) {
        return Collections.emptyList();
    }
}
