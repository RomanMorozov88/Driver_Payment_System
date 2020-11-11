package morozov.ru.controller;

import morozov.ru.model.Payment;
import morozov.ru.model.util.ControlPeriod;
import morozov.ru.model.util.Report;
import morozov.ru.service.serviceinterface.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/dps/payments/{id}")
    public List<Payment> getForPeriod(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestBody ControlPeriod period
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return paymentService.getPaymentsForPeriod(id, period, pageable).getContent();
    }

    @GetMapping("/dps/payments/total/{id}")
    public Report getTotalForPeriod(@PathVariable Integer id, @RequestBody ControlPeriod period) {
        return paymentService.getReport(id, period);
    }

}
