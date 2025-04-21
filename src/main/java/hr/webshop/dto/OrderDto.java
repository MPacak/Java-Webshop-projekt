package hr.webshop.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto implements Serializable {
    private Integer id;
    private Integer userId;
    private Double total;
    private LocalDateTime timestamp;
    private String status;
    private Integer paymentMethodId;
    private String paymentMethodName;
    private List<OrderItemsDto> orderItems;
}