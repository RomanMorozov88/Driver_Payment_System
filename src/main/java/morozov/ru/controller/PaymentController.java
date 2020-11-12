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
@RequestMapping("/dps")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Получение подробного списка операций за период.
     * Pagination
     *
     * @param id     - id счёта, по которому нажна информация.
     * @param page   - номер страницы для просмотра.
     * @param size   - размер странцы.
     * @param period - за какой период.
     * @return
     */
    @GetMapping("/payments/{id}")
    public List<Payment> getForPeriod(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestBody ControlPeriod period
    ) {
        List<Payment> result = null;
        if (this.checkPeriod(period)) {
            Pageable pageable = PageRequest.of(page, size);
            result = paymentService.getPaymentsForPeriod(id, period, pageable);
        }
        return result;
    }

    /**
     * получение оборота за период по отдельному лицевому счету (дебет, кредит отдельно)
     *
     * @param id     - id счёта, по которому нажна информация.
     * @param period - за какой период.
     * @return
     */
    @GetMapping("/payments/total/{id}")
    public Report getTotalForPeriod(@PathVariable Integer id, @RequestBody ControlPeriod period) {
        Report result = null;
        if (this.checkPeriod(period)) {
            result = paymentService.getReport(id, period);
        }
        return result;
    }

    private boolean checkPeriod(ControlPeriod period) {
        boolean result = false;
        if (period != null && period.getStart() != null && period.getEnd() != null) {
            result = true;
        }
        return result;
    }

}
