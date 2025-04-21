package hr.webshop.irepository;

import hr.webshop.model.LoginLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggingRepository extends JpaRepository<LoginLogs, Integer> {
}
