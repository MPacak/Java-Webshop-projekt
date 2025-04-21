
package hr.webshop.service;

import hr.webshop.dto.OrderDto;
import hr.webshop.dto.OrderItemsDto;
import hr.webshop.dto.ProductDto;
import hr.webshop.dto.ProductQuantityDto;
import hr.webshop.irepository.OrderRepository;
import hr.webshop.irepository.UserRepository;
import hr.webshop.iservice.OrderService;
import hr.webshop.mapper.AppMapper;
import hr.webshop.model.Order;
import hr.webshop.model.OrderItem;
import hr.webshop.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private OrderRepository orderRepository;
    private AppMapper appMapper;

    @Override
    public void addProductToOrder(OrderDto order, ProductDto product) {
            List<OrderItemsDto> items = order.getOrderItems();

            for (OrderItemsDto item : items) {
                if (item.getProductId().equals(product.getId())) {
                    item.setQuantity(item.getQuantity() + 1);
                    return;
                }
            }
            OrderItemsDto newItem = new OrderItemsDto();
            newItem.setProductId(product.getId());
            newItem.setProductName(product.getName());
            newItem.setImagePath(product.getImagePath());
            newItem.setPurchasePrice(product.getPrice());
            newItem.setQuantity(1);
            newItem.setSubTotal(product.getPrice());
            items.add(newItem);
    }

    @Override
    public void removeProductFromOrder(OrderDto order, Integer productId) {
        List<OrderItemsDto> items = order.getOrderItems();
        if (!items.isEmpty()) {
            items.removeIf(o -> o.getProductId().equals(productId));
        }
    }

    @Override
    public void removeAllProductsFromOrder(OrderDto order) {
        List<OrderItemsDto> items = order.getOrderItems();
        items.removeAll(order.getOrderItems());
    }

    @Override
    public Double calculateSubtotal(OrderDto order, ProductQuantityDto updateDto) {
        Double subtotal = 0.00;
        for (OrderItemsDto item : order.getOrderItems()) {
            if (item.getProductId().equals(updateDto.getProductId())) {
                item.setQuantity(updateDto.getQuantity());
                item.setSubTotal(item.getQuantity() * item.getPurchasePrice());
                subtotal = item.getSubTotal();
                break;
            }

        }
        return subtotal;
    }

    @Override
    public OrderDto createOrder() {
       OrderDto order = new OrderDto();
        order.setOrderItems(new ArrayList<>());
        order.setTotal(0.0);
        return order;
    }

    @Override
    public OrderDto finalizeOrder(OrderDto order) {

        order.setStatus("PLACED");
        order.setTimestamp(LocalDateTime.now());
        return order;
    }

    @Override
    public List<OrderDto> getAllOrdersByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return orderRepository.findByUser(user)
                .stream()
                .map(order -> appMapper.toOrderDto(order))
                .toList();
    }
    @Override
    public void saveOrder(OrderDto orderDto) {
        Order order = appMapper.toOrderEntity(orderDto);
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                item.setOrder(order);
            }
        }

        orderRepository.save(order);
    }

    @Override
    public void recalculateTotal(OrderDto order) {
        double total = order.getOrderItems().stream()
                .mapToDouble(item -> item.getPurchasePrice() * item.getQuantity())
                .sum();
        order.setTotal(total);
    }

    @Override
    public OrderDto getOrderById(Integer id) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id " + id));
        return appMapper.toOrderDto(order);
    }
}

