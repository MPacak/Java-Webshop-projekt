package hr.webshop.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class OrderLogDto {
    private Long id;
    private String username;
    private String ipAddress;
    private String paymentMethod;
    private Instant timestamp;
    private List<OrderItemsDto> items;
}
