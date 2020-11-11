package morozov.ru.service.serviceimplements;

import morozov.ru.model.Payment;
import morozov.ru.model.util.ControlPeriod;
import morozov.ru.model.util.Report;
import morozov.ru.service.repository.PaymentRepository;
import morozov.ru.service.serviceinterface.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Report getReport(int accountId, ControlPeriod period) {
        Report result = new Report();
        result.setDebit(
                paymentRepository.getTotalSum(accountId, true, period.getStart(), period.getEnd())
        );
        result.setCredit(
                paymentRepository.getTotalSum(accountId, false, period.getStart(), period.getEnd())
        );
        return result;
    }

    @Override
    public List<Payment> getPaymentsForPeriod(int accountId, ControlPeriod period) {
        return paymentRepository.getPaymentsForPeriod(accountId, period.getStart(), period.getEnd());
    }
}