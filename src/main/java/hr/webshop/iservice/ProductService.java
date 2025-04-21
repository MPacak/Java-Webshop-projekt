package hr.webshop.iservice;

import hr.webshop.dto.CategoryDto;
import hr.webshop.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDto save(ProductDto productDto);
    Optional<ProductDto> update(ProductDto productDto);
    void delete(Integer id);
    ProductDto getById(Integer id);
    List<ProductDto> getAllProducts();
    List<CategoryDto> getAllCategories();
    List<ProductDto> filterProducts(String productName, Integer categoryId);
}
