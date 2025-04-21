package hr.webshop.iservice;

import hr.webshop.dto.OrderItemsDto;
import hr.webshop.dto.OrderLogDto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface OrderLogService {
    void logOrder(String username, String ipAddress, List<OrderItemsDto> items, String paymentMethod, Instant timestamp);
    List<OrderLogDto> findByCriteria(String username, LocalDate from, LocalDate to);
}
