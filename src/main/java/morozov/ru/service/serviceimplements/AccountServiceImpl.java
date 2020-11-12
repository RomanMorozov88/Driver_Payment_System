package morozov.ru.service.serviceimplements;

import morozov.ru.model.Account;
import morozov.ru.model.Payment;
import morozov.ru.service.repository.AccountRepository;
import morozov.ru.service.serviceinterface.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;

import static java.math.BigDecimal.valueOf;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class AccountServiceImpl implements AccountService {

    @Value("${payment.debit}")
    private String debitKey;
    @Value("${payment.credit}")
    private String creditKey;

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getById(int accountId) {
        return accountRepository.getById(accountId);
    }

    /**
     * Перевод между счетами. Делается простая проверка накорректность полученных данных.
     * Далее происходит операция списания- если она успешна- то затем идёт
     * операция зачисления.
     *
     * @param ownerId
     * @param accountIdFrom
     * @param accountIdTo
     * @param sum
     * @return
     */
    @Override
    public boolean internalTransfer(int ownerId, int accountIdFrom, int accountIdTo, BigDecimal sum) {
        boolean result = false;
        Account fromAccount = accountRepository.getById(accountIdFrom);
        Account toAccount = accountRepository.getById(accountIdTo);
        if (
                fromAccount != null
                        && toAccount != null
                        && fromAccount.getOwner().getId() == ownerId
                        && toAccount.getOwner().getId() == ownerId
        ) {
            if (
                    this.subWithdrawals(
                            fromAccount,
                            new Payment(creditKey, sum, Calendar.getInstance())
                    )
            ) {
                this.subTransfer(toAccount, new Payment(debitKey, sum, Calendar.getInstance()));
                result = true;
            }
        }
        return result;
    }

    /**
     * Зачисление\Списание средств.
     * В зависимости от типа операции-
     * debit- зачисление (subTransfer метод)
     * credit- списание (subWithdrawals метод)
     *
     * @param accountId
     * @param payment
     * @return
     */
    @Override
    public boolean transfer(int accountId, Payment payment) {
        boolean result = false;
        Account targetAccount = accountRepository.getById(accountId);
        if (targetAccount != null) {
            if (payment.getOperation().equals(debitKey)) {
                this.subTransfer(targetAccount, payment);
                result = true;
            } else if ((payment.getOperation().equals(creditKey))) {
                result = this.subWithdrawals(targetAccount, payment);
            }
        }
        return result;
    }

    @Override
    public void delete(int accountId) {
        accountRepository.deleteById(accountId);
    }

    /**
     * Методля для зачисления средств
     *
     * @param targetAccount
     * @param payment
     */
    private void subTransfer(Account targetAccount, Payment payment) {
        BigDecimal currentSum = targetAccount.getBalance();
        targetAccount.setBalance(currentSum.add(payment.getSum()));
        payment.setAccount(targetAccount);
        targetAccount.setPayment(payment);
        accountRepository.save(targetAccount);
    }

    /**
     * Метод для списания средств
     * Если средств недостаточно- вернёт false.
     *
     * @param targetAccount
     * @param payment
     * @return
     */
    private boolean subWithdrawals(Account targetAccount, Payment payment) {
        boolean result = false;
        BigDecimal newBalance = targetAccount.getBalance().subtract(payment.getSum());
        if (newBalance.compareTo(valueOf(0)) >= 0) {
            targetAccount.setBalance(newBalance);
            payment.setAccount(targetAccount);
            targetAccount.setPayment(payment);
            accountRepository.save(targetAccount);
            result = true;
        }
        return result;
    }
}
