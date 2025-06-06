package hr.webshop.iservice;

import hr.webshop.dto.OrderDto;
import hr.webshop.dto.ProductDto;
import hr.webshop.dto.ProductQuantityDto;

import java.util.List;

public interface OrderService {
    void addProductToOrder(OrderDto order, ProductDto product);
    void removeProductFromOrder(OrderDto order, Integer productId);
    void removeAllProductsFromOrder(OrderDto order);
    Double calculateSubtotal(OrderDto order, ProductQuantityDto updateDto);
    OrderDto createOrder();
    OrderDto finalizeOrder(OrderDto order);
    List<OrderDto> getAllOrdersByUsername(String username);
    void saveOrder(OrderDto order);
    void recalculateTotal(OrderDto order);
    OrderDto getOrderById(Integer id);
}
