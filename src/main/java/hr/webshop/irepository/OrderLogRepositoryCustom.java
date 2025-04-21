package hr.webshop.irepository;

import hr.webshop.model.OrderLog;

import java.time.Instant;
import java.util.List;

public interface OrderLogRepositoryCustom {
    List<OrderLog> findWithFilters(String username, Instant from, Instant to);
}
