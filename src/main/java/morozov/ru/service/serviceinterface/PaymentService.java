package morozov.ru.service.serviceinterface;

import morozov.ru.model.Payment;
import morozov.ru.model.util.ControlPeriod;
import morozov.ru.model.util.Report;

import java.util.List;

public interface PaymentService {

    Report getReport(int accountId, ControlPeriod period);

    List<Payment> getPaymentsForPeriod(int accountId, ControlPeriod period);
}