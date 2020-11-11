package morozov.ru.controller;

import morozov.ru.model.Payment;
import morozov.ru.model.util.ControlPeriod;
import morozov.ru.model.util.Report;
import morozov.ru.service.serviceinterface.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/dps/payments/{id}")
    public List<Payment> getForPeriod(@PathVariable Integer id, @RequestBody ControlPeriod period) {
        return paymentService.getPaymentsForPeriod(id, period);
    }

    @GetMapping("/dps/payments/total/{id}")
    public Report getTotalForPeriod(@PathVariable Integer id, @RequestBody ControlPeriod period) {
        return paymentService.getReport(id, period);
    }

}
