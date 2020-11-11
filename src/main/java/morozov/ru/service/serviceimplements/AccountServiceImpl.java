package morozov.ru.service.serviceimplements;

import morozov.ru.model.Account;
import morozov.ru.model.Payment;
import morozov.ru.service.repository.AccountRepository;
import morozov.ru.service.serviceinterface.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repository;

    @Override
    public boolean internalTransfer(int ownerId, int accountIdFrom, int accountIdTo, double sum) {
        return false;
    }

    @Override
    public boolean transfer(int accountId, double sum) {
        boolean result = false;
        Account targetAccount = repository.getById(accountId);
        if (targetAccount != null) {
            double currentSum = targetAccount.getBalance();
            targetAccount.setBalance(currentSum + sum);
            Payment newPayment = new Payment();
            //newPayment.setAccount(targetAccount);
            newPayment.setDebit(true);
            newPayment.setSum(sum);
            newPayment.setCreated(Calendar.getInstance());
            targetAccount.setPayment(newPayment);
            repository.save(targetAccount);
            result = true;
        }
        return result;
    }

    @Override
    public boolean withdrawals(int accountId, double sum) {
        boolean result = false;
        Account targetAccount = repository.getById(accountId);
        if (targetAccount != null) {
            double currentSum = targetAccount.getBalance();
            if (currentSum - sum >= 0) {
                targetAccount.setBalance(currentSum - sum);
                Payment newPayment = new Payment();
                newPayment.setDebit(false);
                newPayment.setSum(sum);
                newPayment.setCreated(Calendar.getInstance());
                targetAccount.setPayment(newPayment);
                repository.save(targetAccount);
                result = true;
            }
        }
        return result;
    }

    @Override
    public List<Payment> getAllForPeriod(int accountId, Calendar start, Calendar end) {
        return null;
    }
}
