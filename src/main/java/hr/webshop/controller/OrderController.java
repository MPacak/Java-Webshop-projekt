package hr.webshop.controller;
import hr.webshop.dto.*;
import hr.webshop.iservice.OrderService;
import hr.webshop.iservice.PaymentMethodService;
import hr.webshop.iservice.ProductService;
import hr.webshop.iservice.UserService;
import hr.webshop.model.PaymentMethod;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/webshop")
@AllArgsConstructor
public class OrderController {
    private ProductService productService;
    private UserService userService;
    private OrderService orderService;
    private PaymentMethodService paymentMethodService;
    private static final String SESSIONORDER = "order";
    @GetMapping("/order/add/{productId}")
    public String addToCart(@PathVariable Integer productId, HttpSession session) {
        OrderDto order = (OrderDto) session.getAttribute(SESSIONORDER);

        if (order == null) {
            order = orderService.createOrder();
        }

        ProductDto product = productService.getById(productId);
        orderService.addProductToOrder(order, product);
        session.setAttribute(SESSIONORDER, order);

        return "redirect:/webshop/order/cart";
    }
    @GetMapping("/order/cart")
    public String showCart(HttpSession session, Model model) {
        OrderDto order = (OrderDto) session.getAttribute(SESSIONORDER);

        if (order == null) {
            order = orderService.createOrder();
        } else {
            orderService.recalculateTotal(order);
        }

        model.addAttribute("order", order);
        return "order/cart";
    }
    @PostMapping("/order/update")
    @ResponseBody
    public Map<String, Object> updateQuantity(@RequestBody ProductQuantityDto updateDto, HttpSession session) {
        OrderDto order = (OrderDto) session.getAttribute(SESSIONORDER);
        if (order != null && updateDto.getProductId() != null && updateDto.getQuantity() != null) {
         Double subTotal = orderService.calculateSubtotal(order, updateDto);
           orderService.recalculateTotal(order);
            session.setAttribute(SESSIONORDER, order);
            Map<String, Object> response = new HashMap<>();
        response.put("total", order.getTotal());
            response.put("subtotal", subTotal);
            response.put("productId", updateDto.getProductId());
            return response;
        }
        return Collections.singletonMap("error", "Invalid request");
    }
    @GetMapping("/order/checkout")
    public String showCheckoutPage(HttpSession session, Model model, Principal principal) {
        OrderDto order = (OrderDto) session.getAttribute("order");

        if (order == null || order.getOrderItems().isEmpty()) {
            return "redirect:/webshop/order/cart";
        }
        UserDto user = userService.getUserByUsername(principal.getName());
        List<PaymentMethod> paymentMethods = paymentMethodService.getAll();
        model.addAttribute("order", order);
        model.addAttribute("user", user);
        model.addAttribute("paymentMethods", paymentMethods);

        return "order/checkout";
    }
    @PostMapping("/order/confirm")
    public String confirmOrder(@RequestParam Integer paymentMethodId,HttpSession session,Principal principal) {

        OrderDto order = (OrderDto) session.getAttribute("order");

        if (order == null || order.getOrderItems().isEmpty()) {
            return "redirect:/webshop/order/cart";
        }
        if (order.getUserId() == null) {
            UserDto user = userService.getUserByUsername(principal.getName());
            order.setUserId(user.getId());
        }
        PaymentMethod method = paymentMethodService.getById(paymentMethodId);
        order.setPaymentMethodId(paymentMethodId);
        if (method.getPaymentName().equalsIgnoreCase("Paypal")) {
            session.setAttribute(SESSIONORDER, order);
            return "redirect:/webshop/order/paypal";
        } else {
            orderService.saveOrder(orderService.finalizeOrder(order));
            return "redirect:/webshop/order/confirmation";
        }
    }
    @GetMapping("/order/confirmation")
    public String showOrderConfirmation() {
        return "order/confirmation";
    }
    @PostMapping("/order/remove/{productId}")
    public String removeFromCart(@PathVariable Integer productId, HttpSession session) {
        OrderDto order = (OrderDto) session.getAttribute(SESSIONORDER);
        orderService.removeProductFromOrder(order,productId);
        return "redirect:/webshop/order/cart";
    }
    @PostMapping("/order/remove")
    public String removeAllFromCart(HttpSession session) {
        OrderDto order = (OrderDto) session.getAttribute(SESSIONORDER);
        orderService.removeAllProductsFromOrder(order);
        return "redirect:/webshop/order/cart";
    }
    @GetMapping("/order/myorders")
    public String showUserOrders(Model model, Principal principal) {
        List<OrderDto> orders = orderService.getAllOrdersByUsername(principal.getName());
        model.addAttribute("orders", orders);
        return "order/myOrders";
    }
    @GetMapping("/order/myOrderDetails")
    public String detailOrder(@RequestParam Integer orderId, Model model) {
        OrderDto order = orderService.getOrderById(orderId);
        model.addAttribute("orderdetail", order);
        return "order/myOrderDetails";
    }
//check if any of the quantites are 0! it cannot be zero or put validation!!! and bindresult!!!


}
