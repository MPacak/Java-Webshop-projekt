package hr.webshop.iservice;

import hr.webshop.model.PaymentMethod;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethod> getAll();
    PaymentMethod getById(int id);
}
