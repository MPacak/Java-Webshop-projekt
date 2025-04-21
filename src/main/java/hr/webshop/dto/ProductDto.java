package hr.webshop.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class ProductDto {
    private Integer id;
    @NotEmpty(message ="Product name cannot be empty")
    private String name;
    private String description;
    @NotNull(message = "You must set a price")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    private Double price;
    private Integer categoryId;
    private String categoryName;
    private String imagePath;
}
