package hr.webshop.service;

import hr.webshop.irepository.PaymentMethodRepository;
import hr.webshop.iservice.PaymentMethodService;
import hr.webshop.model.PaymentMethod;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethod> getAll() {

        return paymentMethodRepository.findAll();
    }

    @Override
    public PaymentMethod getById(int id) {
        return paymentMethodRepository.getReferenceById(id);
    }
}

