package morozov.ru.controller;

import morozov.ru.model.Payment;
import morozov.ru.model.util.ControlPeriod;
import morozov.ru.service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentController {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

//    @GetMapping("/dps/payments/{id}")
//    public List<Payment> getForPeriod(@PathVariable Integer id, @RequestBody ControlPeriod period) {
//        return paymentRepository.getPaymentsForPeriod(id, period.getStart(), period.getEnd());
//    }
//
//    @GetMapping("/dps/payments/total/{id}")
//    public Double getTotalForPeriod(@PathVariable Integer id, @RequestBody ControlPeriod period) {
//        return paymentRepository.getTotalSum(id, true, period.getStart(), period.getEnd());
//    }

}
