package morgan.learn.feign.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import morgan.learn.feign.service.RemoteRecommendService;
import morgan.learn.feign.model.Product;
import morgan.learn.feign.model.Recommendation;
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
public class ProductController {
//    @Autowired
//    private SetProcTimeBean setProcTimeBean;


    @RequestMapping("/product/{productId}")
    @HystrixCommand(fallbackMethod = "fallBackProduct", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
    })
    public Product getProduct(@PathVariable int productId) throws InterruptedException {

        int pt = (int) (1000 * Math.random());//setProcTimeBean.calculateProcessingTime();
        log.info("/product called, processing time: {}", pt);

        Thread.sleep(pt);

        log.debug("/product return the found product");
        return new Product(productId, "name", 123);
    }

    public Product fallBackProduct(int productId) {
        return new Product(productId, "empty", 0);
    }
}
