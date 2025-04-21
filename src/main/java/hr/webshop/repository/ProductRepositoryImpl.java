package hr.webshop.repository;

import hr.webshop.irepository.ProductRepositoryCustom;
import hr.webshop.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> filterByName(String name, Integer categoryId) {
        StringBuilder queryStr = new StringBuilder("SELECT p FROM Product p WHERE 1=1");
        Map<String,Object> params = new HashMap<>();
        if (name != null) {
            queryStr.append(" AND LOWER(p.name) LIKE LOWER(:name)");
            params.put("name", "%" + name + "%");
        }
        if (categoryId != null) {
            queryStr.append(" AND p.category.id = :categoryId");
            params.put("categoryId", categoryId);
        }


        TypedQuery<Product> query = entityManager.createQuery(queryStr.toString(), Product.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }
}

