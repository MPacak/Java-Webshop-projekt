package hr.webshop.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginDto {
    private Integer userId;
    private String username;
    private String ipAddress;

    private LocalDateTime loginTime;
}
