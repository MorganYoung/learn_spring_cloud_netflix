package morgan.learn.eureka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by morgan on 16/10/8.
 */
@RestController
@RefreshScope
public class Controller {

    @Value("${words}")
    String words;

    @RequestMapping("/")
    String getWords() {
        String[] wordArray = words.split(",");
        int i = (int)Math.round(Math.random() * (wordArray.length - 1));
        return wordArray[i];
    }
}
