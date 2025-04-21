package hr.webshop.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class OrderItemsDto {
    private Integer id;

    private Integer productId;
    private String productName;
    private String imagePath;
    @Min(value = 1)
    private Integer quantity;
    private Double purchasePrice;
    private Double subTotal;
}
