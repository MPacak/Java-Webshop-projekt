package hr.webshop.component;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PayPalClient {
    /**
     * -- GETTER --
     * expose the initialized client
     */
    private final PayPalHttpClient httpClient;

    public PayPalClient(
            @Value("${paypal.client-id}") String clientId,
            @Value("${paypal.client-secret}") String clientSecret,
            @Value("${paypal.mode}") String mode
    ) {
        PayPalEnvironment env = "live".equalsIgnoreCase(mode)
                ? new PayPalEnvironment.Live(clientId, clientSecret)
                : new PayPalEnvironment.Sandbox(clientId, clientSecret);

        this.httpClient = new PayPalHttpClient(env);
    }

}
