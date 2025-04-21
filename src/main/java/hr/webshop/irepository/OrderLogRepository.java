package hr.webshop.irepository;

import hr.webshop.model.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface OrderLogRepository extends JpaRepository<OrderLog, Integer>, OrderLogRepositoryCustom {
    List<OrderLog> findByTimestampBetween(Instant start, Instant end);
}
