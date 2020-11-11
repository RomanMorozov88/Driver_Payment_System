package morozov.ru.service.serviceinterface;

import morozov.ru.model.Account;
import morozov.ru.model.Payment;

public interface AccountService {

    Account getById(int accountId);

    /**
     * Перевод между своими счетами
     *
     * @param ownerId
     * @param accountIdFrom
     * @param accountIdTo
     * @param sum
     * @return
     */
    boolean internalTransfer(int ownerId, int accountIdFrom, int accountIdTo, double sum);

    /**
     * Зачисление\списание средств.
     * Операция определяется по полю debit объекта payment
     *
     * @param accountId
     * @param payment
     * @return
     */
    boolean transfer(int accountId, Payment payment);

    void delete(int accountId);
}