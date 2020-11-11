package morozov.ru.service.serviceinterface;

import morozov.ru.model.Payment;

import java.util.Calendar;
import java.util.List;

public interface AccountService {

    boolean internalTransfer(int ownerId, int accountIdFrom, int accountIdTo, double sum);

    boolean transfer(int accountId, double sum);

    boolean withdrawals(int accountId, double sum);

    List<Payment> getAllForPeriod(int accountId, Calendar start, Calendar end);
}