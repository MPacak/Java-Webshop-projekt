package hr.webshop.controller;

import hr.webshop.dto.LoginDto;
import hr.webshop.dto.OrderLogDto;
import hr.webshop.iservice.LoggingService;
import hr.webshop.iservice.OrderLogService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/webshop")
public class LogController {
    private final LoggingService loggingService;
    private OrderLogService orderLogService;
    @GetMapping("/admin/logs")
    public String showLoginLogs(Model model) {
        List<LoginDto> loginLogs = loggingService.getAllLoginLogs();
        model.addAttribute("logs", loginLogs);
        return "admin/logs";
    }
    @GetMapping("/admin/orderLogs")
    public String getAll(
            @RequestParam(required = false) String customer,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to, Model model) {
        List<OrderLogDto> logs = orderLogService.findByCriteria(customer, from, to);
        model.addAttribute("orderLogs", logs);
        model.addAttribute("customer", customer);
        model.addAttribute("from", from);
        model.addAttribute("to", to);

        return "admin/orderLog";
    }
}
