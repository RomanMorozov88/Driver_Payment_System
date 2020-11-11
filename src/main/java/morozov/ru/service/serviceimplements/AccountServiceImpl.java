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

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean internalTransfer(int ownerId, int accountIdFrom, int accountIdTo, double sum) {
        boolean result = false;
        Account fromAccount = accountRepository.getById(accountIdFrom);
        Account toAccount = accountRepository.getById(accountIdTo);
        if (fromAccount.getOwner().getId() == ownerId && toAccount.getOwner().getId() == ownerId) {
            if (
                    this.subWithdrawals(
                            fromAccount,
                            new Payment(false, sum, Calendar.getInstance())
                    )
            ) {
                this.subTransfer(toAccount, new Payment(true, sum, Calendar.getInstance()));
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean transfer(int accountId, Payment payment) {
        boolean result = false;
        Account targetAccount = accountRepository.getById(accountId);
        if (targetAccount != null) {
            if (payment.isDebit()) {
                this.subTransfer(targetAccount, payment);
                result = true;
            } else {
                result = this.subWithdrawals(targetAccount, payment);
            }
        }
        return result;
    }

    @Override
    public void delete(int accountId) {
        accountRepository.deleteById(accountId);
    }

    private void subTransfer(Account targetAccount, Payment payment) {
        double currentSum = targetAccount.getBalance();
        targetAccount.setBalance(currentSum + payment.getSum());
        payment.setAccount(targetAccount);
        targetAccount.setPayment(payment);
        accountRepository.save(targetAccount);
    }

    private boolean subWithdrawals(Account targetAccount, Payment payment) {
        boolean result = false;
        double newBalance = targetAccount.getBalance() - payment.getSum();
        if (newBalance >= 0) {
            targetAccount.setBalance(newBalance);
            payment.setAccount(targetAccount);
            targetAccount.setPayment(payment);
            accountRepository.save(targetAccount);
            result = true;
        }
        return result;
    }
}
