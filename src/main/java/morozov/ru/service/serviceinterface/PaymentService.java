package morozov.ru.service.serviceinterface;

import morozov.ru.model.Payment;
import morozov.ru.model.util.ControlPeriod;
import morozov.ru.model.util.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    Report getReport(int accountId, ControlPeriod period);

    Page<Payment> getPaymentsForPeriod(int accountId, ControlPeriod period, Pageable pageable);
}