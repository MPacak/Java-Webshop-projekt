package hr.webshop.service;

import hr.webshop.dto.CategoryDto;
import hr.webshop.dto.ProductDto;


import hr.webshop.irepository.CategoryRepository;
import hr.webshop.irepository.ProductRepository;
import hr.webshop.iservice.ProductService;
import hr.webshop.mapper.AppMapper;
import hr.webshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final AppMapper appMapper;

    @Autowired
   public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, AppMapper appMapper) {
       this.productRepository = productRepository;
       this.categoryRepository = categoryRepository;
       this.appMapper = appMapper;
   }
    @Override
    public ProductDto save(ProductDto dto) {
        Product product = productRepository.save(appMapper.toProductEntity(dto));
        return appMapper.toProductDto(product);
    }

    @Override
    public Optional<ProductDto> update(ProductDto item) {
        productRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
         categoryRepository.findById(item.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = productRepository.save(appMapper.toProductEntity(item));
        return Optional.of(appMapper.toProductDto(product));
    }

    @Override
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto getById(Integer id) {
        return appMapper.toProductDto(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found")));
    }

@Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(appMapper::toProductDto)
                .toList();
}

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(appMapper::toCategoryDto)
                .toList();
    }
    @Override
    public List<ProductDto> filterProducts(String productName, Integer categoryId) {
    return productRepository.filterByName(productName, categoryId)
            .stream()
            .map(appMapper::toProductDto)
            .toList();
    }


}
