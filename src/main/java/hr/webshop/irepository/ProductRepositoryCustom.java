package hr.webshop.irepository;

import hr.webshop.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> filterByName(String name, Integer categoryId);


}
