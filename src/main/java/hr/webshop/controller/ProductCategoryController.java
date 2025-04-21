package hr.webshop.controller;

import hr.webshop.dto.CategoryDto;
import hr.webshop.dto.ProductDto;
import hr.webshop.iservice.CategoryService;
import hr.webshop.iservice.ProductService;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/webshop")
@AllArgsConstructor
public class ProductCategoryController {

    private ProductService productService;
    private CategoryService categoryService;

    @GetMapping("/products")
    public String showProductList(@RequestParam(required = false) String productName, @RequestParam(required = false) Integer categoryId, Model model) {
        List<ProductDto> products = productService.filterProducts(productName, categoryId);
        List<CategoryDto> categories = categoryService.getAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "productList";
    }

    @GetMapping("/categories")
public String showCategoryList(Model model) {
        List<CategoryDto> categories = categoryService.getAll();
        model.addAttribute("categories", categories);
        return "categoryList";
    }
}
