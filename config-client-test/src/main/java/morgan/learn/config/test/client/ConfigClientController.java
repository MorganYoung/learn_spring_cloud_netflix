package morgan.learn.config.test.client;

import morgan.learn.config.test.client.model.MorganLearn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by morgan on 16/10/8.
 */
@RestController
@RefreshScope
public class ConfigClientController {

    @Value("${morgan.learn.valueName}")
    String value;

    @Autowired
    private MorganLearn morganLearn;

    @RequestMapping("/test")
    public MorganLearn test() {
        return morganLearn;
    }

    @RequestMapping("/test2")
    public String test2() {
        return value;
    }
}
