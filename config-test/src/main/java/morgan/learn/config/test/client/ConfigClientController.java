package morgan.learn.config.test.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by morgan on 16/10/8.
 */
@RestController

public class ConfigClientController {

    @Value("")
    private String valueName;

}
