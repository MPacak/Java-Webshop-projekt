package hr.webshop.controller;

import hr.webshop.dto.CategoryDto;
import hr.webshop.dto.ProductDto;
import hr.webshop.iservice.CategoryService;
import hr.webshop.iservice.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/webshop")
@AllArgsConstructor
public class ProductCategoryAdminController {
    private ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/admin/products")
    public String getAllProductsForAdmin(Model model) {
       model.addAttribute("products", productService.getAllProducts());
        return "/admin/products";
    }
    @GetMapping("/admin/products/add")
    public String addProduct(Model model) {
        model.addAttribute("productForm", new ProductDto());
        model.addAttribute("categories", productService.getAllCategories());
        return "admin/productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String saveProduct(@Valid @ModelAttribute("productForm") ProductDto dto,
                              BindingResult bindingResult, Model model,  @RequestParam("image") MultipartFile image) {
        if (bindingResult.hasErrors() && image.isEmpty()) {
            model.addAttribute("categories", productService.getAllCategories());
            return "admin/productsAdd";
        }
        dto.setImagePath(image.getOriginalFilename());
        productService.save(dto);
        return "redirect:/webshop/admin/products";
    }

    @GetMapping("/admin/products/update")
    public String updateProduct(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("productForm", productService.getById(id));
        model.addAttribute("categories", productService.getAllCategories());
        return "admin/productEdit";
    }

    @PostMapping("/admin/products/update")
    public String updateProduct(@Valid @ModelAttribute("productForm") ProductDto dto,
                                BindingResult bindingResult, Model model, @RequestParam("image") MultipartFile image) {
        ProductDto originalDto = productService.getById(dto.getId());
        if (image.isEmpty()) {
            dto.setImagePath(originalDto.getImagePath());
        } else {
            dto.setImagePath(image.getOriginalFilename());
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", productService.getAllCategories());
            return "admin/productEdit";
        }
        productService.update(dto);
        return "redirect:/webshop/admin/products";
    }


    @PostMapping("/admin/products/delete")
    public String deleteProduct(@RequestParam("id") Integer id) {
        productService.delete(id);
        return "redirect:/webshop/admin/products";
    }

    @GetMapping("/admin/categories")
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "admin/categories";
    }

    @GetMapping("/admin/categories/add")
    public String addCategory(Model model) {
        model.addAttribute("categoryForm", new CategoryDto());
        return "admin/categoryAdd";
    }

    @PostMapping("/admin/categories/add")
    public String saveCategory(@Valid @ModelAttribute("categoryForm") CategoryDto categoryDto,
                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "admin/categoryAdd";
        }
        categoryService.save(categoryDto);
        return "redirect:/webshop/admin/categories";
    }

    @GetMapping("/admin/categories/update")
    public String editCategory(@RequestParam("id") Integer id, Model model) {
        categoryService.getById(id).ifPresent(cat -> model.addAttribute("categoryForm", cat));
        return "admin/categoryEdit";
    }

    @PostMapping("/admin/categories/update")
    public String updateCategory(@Valid @ModelAttribute("categoryForm") CategoryDto dto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/categoryEdit";
        }
        categoryService.update(dto);
        return "redirect:/webshop/admin/categories";
    }

    @PostMapping("/admin/categories/delete")
    public String deleteCategory(@RequestParam("id") Integer id) {
        categoryService.delete(id);
        return "redirect:/webshop/admin/categories";
    }
}
