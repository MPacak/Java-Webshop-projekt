package hr.webshop.service;

import com.paypal.orders.*;
import com.paypal.http.HttpResponse;
import hr.webshop.component.PayPalClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PayPalService {

    private final PayPalClient paypalClient;
    private final String returnUrl;
    private final String cancelUrl;
    public PayPalService(
            PayPalClient paypalClient,
            @Value("${paypal.return-url}") String returnUrl,
            @Value("${paypal.cancel-url}") String cancelUrl
    ) {
        this.paypalClient = paypalClient;
        this.returnUrl    = returnUrl;
        this.cancelUrl    = cancelUrl;
    }

    public String createOrder(BigDecimal total) throws IOException {
        OrderRequest orderRequest = new OrderRequest()
                .checkoutPaymentIntent("CAPTURE")
                .purchaseUnits(List.of(
                        new PurchaseUnitRequest()
                                .amountWithBreakdown(
                                        new AmountWithBreakdown()
                                                .currencyCode("USD")
                                                .value(total.toString())
                                )
                ))
                .applicationContext(
                        new ApplicationContext()
                                .returnUrl(returnUrl)
                                .cancelUrl(cancelUrl)
                );

        OrdersCreateRequest request = new OrdersCreateRequest()
                .requestBody(orderRequest);

        HttpResponse<Order> response = paypalClient.getHttpClient().execute(request);
        return response.result()
                .links().stream()
                .filter(l -> "approve".equals(l.rel()))
                .findFirst()
                .map(LinkDescription::href)
                .orElseThrow(() -> new IllegalStateException("No approve link"));
    }

    public boolean captureOrder(String orderId) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(new OrderRequest()); // can be empty
        HttpResponse<Order> response = paypalClient.getHttpClient().execute(request);
        return "COMPLETED".equalsIgnoreCase(response.result().status());
    }
}
