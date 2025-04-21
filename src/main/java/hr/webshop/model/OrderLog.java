package hr.webshop.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_log")
public class OrderLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String ipAddress;
    private String paymentMethod;
    private Instant timestamp;

    @Column(columnDefinition = "TEXT")
    private String itemsJson;

    public OrderLog(String username, String ipAddress, String paymentMethod, Instant timestamp, String itemsJson) {
        this.username = username;
        this.ipAddress = ipAddress;
        this.paymentMethod = paymentMethod;
        this.timestamp = timestamp;
        this.itemsJson = itemsJson;
    }
}
