package hr.webshop.service;

import hr.webshop.dto.CategoryDto;
import hr.webshop.irepository.CategoryRepository;
import hr.webshop.iservice.CategoryService;
import hr.webshop.mapper.AppMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final AppMapper appMapper;

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(appMapper::toCategoryDto)
                .toList();
    }

    @Override
    public void save(CategoryDto dto) {
        categoryRepository.save(appMapper.toCategoryEntity(dto));
    }

    @Override
    public void update(CategoryDto dto) {
        categoryRepository.save(appMapper.toCategoryEntity(dto));
    }

    @Override
    public Optional<CategoryDto> getById(Integer id) {
        return categoryRepository.findById(id).map(appMapper::toCategoryDto);
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }
}
