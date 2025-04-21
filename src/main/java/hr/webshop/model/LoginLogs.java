package hr.webshop.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LoginLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    private String ipAddress;

    private LocalDateTime loginTime;

    public LoginLogs(User user, String ipAddress, LocalDateTime loginTime) {
        this.user = user;
        this.ipAddress = ipAddress;
        this.loginTime = loginTime;
    }
}
