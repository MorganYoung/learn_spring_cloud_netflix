package morgan.learn.config.test.client.model;

import lombok.Data;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by morgan on 16/10/8.
 */
@ConfigurationProperties("morgan.learn")
@Data
@Value
public class MorganLearn {

    private String cfg,url,ver;

}
