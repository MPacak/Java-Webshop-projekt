package hr.webshop.listener;

import hr.webshop.dto.OrderDto;
import hr.webshop.dto.UserDto;
import hr.webshop.iservice.LoggingService;
import hr.webshop.iservice.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
@Component
@AllArgsConstructor
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private UserService userService;
    private LoggingService loggingService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        UserDto user = userService.getUserByUsername(username);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();

        HttpSession session = request.getSession(false);
        if (session != null) {
            OrderDto order = (OrderDto) session.getAttribute("order");
            if (order != null && order.getUserId() == null) {
                order.setUserId(user.getId());
            }
        }

        loggingService.logLogin(user.getId(), ip, LocalDateTime.now());
    }
}
