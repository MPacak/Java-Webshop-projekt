package hr.webshop.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryDto {
    private Integer id;
    @NotEmpty(message = "Choose a name")
    private String name;
}
