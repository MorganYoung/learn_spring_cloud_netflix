package morgan.learn.feign.model;

import lombok.Data;
import lombok.Value;

/**
 * Created by morgan on 16/10/4.
 */
@Data
@Value
public class Product {
    private final String name;
    private final int productId, price;

    public Product(int productId, String name, int price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }
}
