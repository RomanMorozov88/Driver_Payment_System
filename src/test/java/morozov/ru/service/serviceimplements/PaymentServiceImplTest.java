package morozov.ru.service.serviceimplements;

import morozov.ru.model.Account;
import morozov.ru.model.Driver;
import morozov.ru.model.Payment;
import morozov.ru.model.util.ControlPeriod;
import morozov.ru.model.util.Report;
import morozov.ru.service.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestEntityManager
@ComponentScan("morozov.ru")
public class PaymentServiceImplTest {

    @Value("${payment.debit}")
    private String debitKey;
    @Value("${payment.credit}")
    private String creditKey;

    @Autowired
    private PaymentServiceImpl paymentService;
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private DriverServiceImpl driverService;

    private Driver testDriver;
    private Account testAccount;
    private ControlPeriod testPeriod;

    @Before
    public void init() {
        Driver driver = new Driver();
        testDriver = driverService.save(driver);
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(1.1));
        account.setOwner(testDriver);
        testAccount = accountRepository.save(account);
        //Первая операция. Приход.
        Payment paymentOne = new Payment(
                debitKey,
                BigDecimal.valueOf(0.9),
                new GregorianCalendar(2030, Calendar.NOVEMBER, 10,
                        10, 10, 10)
        );
        accountService.transfer(testAccount.getId(), paymentOne);
        //Вторая операция. Приход.
        Payment paymentTwo = new Payment(
                debitKey,
                BigDecimal.valueOf(10),
                new GregorianCalendar(2030, Calendar.NOVEMBER, 1,
                        10, 10, 10)
        );
        accountService.transfer(testAccount.getId(), paymentTwo);
        //Треться операция. Расход.
        Payment paymentThree = new Payment(
                creditKey,
                BigDecimal.valueOf(5),
                new GregorianCalendar(2030, Calendar.NOVEMBER, 8,
                        10, 10, 10)
        );
        accountService.transfer(testAccount.getId(), paymentThree);
        //Четвёртая операция. Расход. Не попадает в тестовый период.
        Payment paymentFour = new Payment(
                creditKey,
                BigDecimal.valueOf(0.5),
                new GregorianCalendar(2030, Calendar.JULY, 5,
                        10, 10, 10)
        );
        accountService.transfer(testAccount.getId(), paymentFour);
        //Тестовый период с 1 по 10 число.
        testPeriod = new ControlPeriod();
        testPeriod.setStart(
                new GregorianCalendar(2030, Calendar.NOVEMBER, 1,
                        5, 10, 10)
        );
        testPeriod.setEnd(
                new GregorianCalendar(2030, Calendar.NOVEMBER, 10,
                        15, 10, 10)
        );
    }

    @Test
    public void getReport() {
        Report result = paymentService.getReport(testAccount.getId(), testPeriod);
        assertEquals(new BigDecimal("10.90"), result.getDebit());
        assertEquals(new BigDecimal("5.00"), result.getCredit());
    }

    @Test
    public void getPaymentsForPeriod() {
        Pageable pageable = PageRequest.of(0, 5);
        assertEquals(
                3,
                paymentService.getPaymentsForPeriod(testAccount.getId(), testPeriod, pageable).size()
        );
    }
}
