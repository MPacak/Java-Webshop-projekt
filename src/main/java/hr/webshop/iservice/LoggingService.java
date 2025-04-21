package hr.webshop.iservice;

import hr.webshop.dto.LoginDto;

import java.time.LocalDateTime;
import java.util.List;

public interface LoggingService {
    void logLogin(Integer id, String ip, LocalDateTime time);

    List<LoginDto> getAllLoginLogs();
}
