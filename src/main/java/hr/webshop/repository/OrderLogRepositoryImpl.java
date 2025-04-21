package hr.webshop.repository;

import hr.webshop.irepository.OrderLogRepositoryCustom;
import hr.webshop.model.OrderLog;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Repository
public class OrderLogRepositoryImpl implements OrderLogRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<OrderLog> findWithFilters(String username, Instant from, Instant to) {
        StringBuilder query = new StringBuilder("SELECT o FROM OrderLog o WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (username != null && !username.isBlank()) {
            query.append("AND o.username = ?1 ");
            params.add(username);
        }

        if (from != null) {
            query.append("AND o.timestamp >= ?").append(params.size() + 1).append(" ");
            params.add(from);
        }

        if (to != null) {
            query.append("AND o.timestamp < ?").append(params.size() + 1).append(" ");
            params.add(to);
        }

        TypedQuery<OrderLog> q = entityManager.createQuery(query.toString(), OrderLog.class);
        for (int i = 0; i < params.size(); i++) {
            q.setParameter(i + 1, params.get(i));
        }

        return q.getResultList();
    }
}
