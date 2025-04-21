package hr.webshop.controller;
import hr.webshop.dto.OrderDto;
import hr.webshop.iservice.OrderService;
import hr.webshop.service.PayPalService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;

@Controller
@RequestMapping("/webshop/order")
@AllArgsConstructor
public class PayPalController {
    private final PayPalService paypalService;
    private final OrderService orderService;

    @GetMapping("/paypal")
    public String paypalCheckout(HttpSession session) throws IOException {
        OrderDto order = (OrderDto) session.getAttribute("order");
        String approvalLink = paypalService.createOrder(BigDecimal.valueOf(order.getTotal()));
        return "redirect:" + approvalLink;
    }

    @GetMapping("/paypal/success")
    public String onSuccess(@RequestParam("token") String paypalOrderId,
                            HttpSession session) throws IOException {
        boolean completed = paypalService.captureOrder(paypalOrderId);
        if (completed) {
            OrderDto order = (OrderDto) session.getAttribute("order");
            orderService.saveOrder(orderService.finalizeOrder(order));
           // session.removeAttribute("order");
            return "redirect:/webshop/order/confirmation";
        }
        return "redirect:/webshop/order/checkout";
    }

    @GetMapping("/paypal/cancel")
    public String onCancel() {
        return "redirect:/webshop/order/cart";
    }
}
