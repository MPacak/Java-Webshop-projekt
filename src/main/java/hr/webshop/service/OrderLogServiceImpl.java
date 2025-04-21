package hr.webshop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hr.webshop.dto.OrderItemsDto;
import hr.webshop.dto.OrderLogDto;
import hr.webshop.irepository.OrderLogRepository;
import hr.webshop.iservice.OrderLogService;
import hr.webshop.mapper.AppMapper;
import hr.webshop.model.OrderLog;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderLogServiceImpl implements OrderLogService {

    private OrderLogRepository repository;
private AppMapper appMapper;
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public void logOrder(String username, String ipAddress, List<OrderItemsDto> items, String paymentMethod, Instant timestamp) {
        String itemsJson;
        try {
            itemsJson = objectMapper.writeValueAsString(items);
        } catch (JsonProcessingException e) {
            itemsJson = "[]";
        }
        OrderLog orderLog = new OrderLog(username, ipAddress, paymentMethod, timestamp, itemsJson);
        repository.save(orderLog);
    }

    @Override
    public List<OrderLogDto> findByCriteria(String username, LocalDate from, LocalDate to) {
        Instant start = (from != null) ? from.atStartOfDay().toInstant(ZoneOffset.UTC) : Instant.EPOCH;
        Instant end = (to != null) ? to.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC) : Instant.now();

        List<OrderLog> audits;
        if (username != null && !username.isEmpty()) {
            audits = repository.findWithFilters(username, start, end);
        } else {
            audits = repository.findByTimestampBetween(start, end);
        }

        return audits.stream() .map(audit -> {
                    OrderLogDto dto = appMapper.toOrderLogDto(audit);

                    List<OrderItemsDto> itemList;
                    try {
                        itemList = objectMapper.readValue(
                                audit.getItemsJson(),
                                objectMapper.getTypeFactory()
                                        .constructCollectionType(List.class, OrderItemsDto.class)
                        );
                    } catch (Exception e) {
                        itemList = List.of();
                    }
                    dto.setItems(itemList);
                    return dto;
                })
                .toList();
    }
}
