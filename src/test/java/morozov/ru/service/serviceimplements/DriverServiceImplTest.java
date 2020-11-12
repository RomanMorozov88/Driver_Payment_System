package morozov.ru.service.serviceimplements;

import morozov.ru.model.Driver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestEntityManager
@ComponentScan("morozov.ru")
public class DriverServiceImplTest {

    @Autowired
    private DriverServiceImpl driverService;

    @Test
    public void getAll() {
        Driver driver1 = new Driver();
        driver1.setName("first driver name");
        Driver driver2 = new Driver();
        driver2.setName("second driver name");
        driverService.save(driver1);
        driverService.save(driver2);
        Pageable pageable = PageRequest.of(0, 5);
        assertEquals(2, driverService.getAll(pageable).size());
    }

    @Test
    public void whenGetById() {
        Driver driver = new Driver();
        driver.setName("driver name");
        int testId = driverService.save(driver).getId();
        Driver savedDriver = driverService.getById(testId);
        assertEquals(testId, savedDriver.getId());
    }

    @Test
    public void whenSave() {
        Driver driver = new Driver();
        driver.setName("driver name");
        Driver savedDriver = driverService.save(driver);
        assertTrue(savedDriver.getId() > 0);
        assertEquals("driver name", savedDriver.getName());
    }

    @Test
    public void whenDelete() {
        Driver driver = new Driver();
        driver.setName("driver name");
        Driver savedDriver = driverService.save(driver);
        driverService.delete(savedDriver.getId());
        Pageable pageable = PageRequest.of(0, 5);
        assertEquals(0, driverService.getAll(pageable).size());
    }

    @Test
    public void whenCreateAccount() {
        Driver driver = new Driver();
        driver.setName("driver name");
        int testId = driverService.save(driver).getId();
        Driver savedDriver = driverService.getById(testId);
        assertEquals(0, savedDriver.getAccounts().size());
        driverService.createAccount(testId);
        savedDriver = driverService.getById(testId);
        assertEquals(1, savedDriver.getAccounts().size());
    }
}
