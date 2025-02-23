package shops.example.shops.shippings.dto;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import lombok.Data;


@Data
public class ShippingDto {
    private UUID shippingId;
    private Long orderId;
    private String shippingMethod;
    private BigDecimal shippingCost;
    private Timestamp shippingDate;
    private Timestamp deliveryDate;
    private String trackingURL;
    private String shippingStatus;

}
