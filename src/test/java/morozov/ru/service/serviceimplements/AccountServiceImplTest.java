package morozov.ru.service.serviceimplements;

import morozov.ru.model.Account;
import morozov.ru.model.Driver;
import morozov.ru.model.Payment;
import morozov.ru.service.repository.AccountRepository;
import morozov.ru.service.repository.DriverRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestEntityManager
@ComponentScan("morozov.ru")
public class AccountServiceImplTest {

    @Value("${payment.debit}")
    private String debitKey;
    @Value("${payment.credit}")
    private String creditKey;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    DriverRepository driverRepository;

    private Driver testDriver;
    private Account testAccount;

    @Before
    public void init() {
        Driver driver = new Driver();
        driver.setName("driver name");
        testDriver = driverRepository.save(driver);
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1.1));
        account.setOwner(testDriver);
        testAccount = accountRepository.save(account);
    }

    @Test
    public void whenGetById() {
        Account account = accountService.getById(testAccount.getId());
        assertEquals(testAccount.getId(), account.getId());
    }

    @Test
    public void whenSuccessInternalTransfer() {
        Account secondAccount = new Account();
        secondAccount.setBalance(BigDecimal.valueOf(1.1));
        secondAccount.setOwner(testDriver);
        secondAccount = accountRepository.save(secondAccount);
        boolean resultOfMethod = accountService.internalTransfer(
                testDriver.getId(),
                testAccount.getId(),
                secondAccount.getId(),
                BigDecimal.valueOf(0.1)
        );
        assertTrue(resultOfMethod);
        assertEquals(new BigDecimal("1.0"), accountService.getById(testAccount.getId()).getBalance());
        assertEquals(new BigDecimal("1.2"), accountService.getById(secondAccount.getId()).getBalance());
    }

    @Test
    public void whenFailInternalTransfer() {
        Account secondAccount = new Account();
        secondAccount.setBalance(BigDecimal.valueOf(1.1));
        secondAccount.setOwner(testDriver);
        secondAccount = accountRepository.save(secondAccount);
        boolean resultOfMethod = accountService.internalTransfer(
                testDriver.getId(),
                testAccount.getId(),
                secondAccount.getId(),
                BigDecimal.valueOf(2)
        );
        assertFalse(resultOfMethod);
        assertEquals(new BigDecimal("1.1"), accountService.getById(testAccount.getId()).getBalance());
        assertEquals(new BigDecimal("1.1"), accountService.getById(secondAccount.getId()).getBalance());
    }

    @Test
    public void whenDebitTransfer() {
        Payment payment = new Payment(debitKey, BigDecimal.valueOf(0.9), Calendar.getInstance());
        boolean resultOfMethod = accountService.transfer(testAccount.getId(), payment);
        assertTrue(resultOfMethod);
        assertEquals(new BigDecimal("2.0"), accountService.getById(testAccount.getId()).getBalance());
    }

    @Test
    public void whenSuccessCreditTransfer() {
        Payment payment = new Payment(creditKey, BigDecimal.valueOf(0.1), Calendar.getInstance());
        boolean resultOfMethod = accountService.transfer(testAccount.getId(), payment);
        assertTrue(resultOfMethod);
        assertEquals(new BigDecimal("1.0"), accountService.getById(testAccount.getId()).getBalance());
    }

    @Test
    public void whenFailCreditTransfer() {
        Payment payment = new Payment(creditKey, BigDecimal.valueOf(2), Calendar.getInstance());
        boolean resultOfMethod = accountService.transfer(testAccount.getId(), payment);
        assertFalse(resultOfMethod);
        assertEquals(new BigDecimal("1.1"), accountService.getById(testAccount.getId()).getBalance());
    }

}
