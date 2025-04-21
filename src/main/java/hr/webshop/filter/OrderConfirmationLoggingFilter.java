package hr.webshop.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.webshop.dto.OrderDto;
import hr.webshop.dto.OrderItemsDto;
import hr.webshop.iservice.OrderLogService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Principal;
import java.time.Instant;
import java.util.List;

@Component
@Order(20)
@RequiredArgsConstructor
@Slf4j
public class OrderConfirmationLoggingFilter implements Filter {

    private final OrderLogService orderLogService;
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();
        String method = req.getMethod();

        if ("/webshop/order/confirmation".equals(path) && "GET".equalsIgnoreCase(method)) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                OrderDto order = (OrderDto) session.getAttribute("order");

                if (order != null) {
                    String ipAddress = req.getRemoteAddr();
                    Principal principal = req.getUserPrincipal();
                    String username = (principal != null) ? principal.getName() : "anonymous";

                    List<OrderItemsDto> items = order.getOrderItems();
                    String paymentMethod = order.getPaymentMethodName();
                    Instant timestamp = Instant.now();

                    orderLogService.logOrder(username, ipAddress,items, paymentMethod, timestamp);
                    session.removeAttribute("order");

                    log.info("Logged order for user '{}' from IP: {}", username, ipAddress);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
