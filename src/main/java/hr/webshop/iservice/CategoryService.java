package hr.webshop.iservice;

import hr.webshop.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDto> getAll();
    void save(CategoryDto dto);
    void update(CategoryDto dto);
    Optional<CategoryDto> getById(Integer id);
    void delete(Integer id);
}
