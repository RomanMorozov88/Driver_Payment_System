package morozov.ru.service.serviceinterface;

import morozov.ru.model.Payment;

public interface AccountService {

    boolean internalTransfer(int ownerId, int accountIdFrom, int accountIdTo, double sum);

    boolean transfer(int accountId, Payment payment);

    void delete(int accountId);
}