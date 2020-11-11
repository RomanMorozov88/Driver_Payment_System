package morozov.ru.controller;

import morozov.ru.model.Account;
import morozov.ru.model.Driver;
import morozov.ru.model.util.ReMessage;
import morozov.ru.service.serviceinterface.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DriverController {

    private final static String SUCCESS_MSG = "Done.";
    private final static String FAIL_MSG = "Something wrong.";

    @Autowired
    private DriverService driverService;

    @GetMapping("/dps/drivers")
    public List<Driver> getAllDrivers() {
        return driverService.getAll();
    }

    @PostMapping("/dps/drivers")
    public Driver saveDriver(@RequestBody Driver driver) {
        return driverService.save(driver);
    }

    @GetMapping("/dps/drivers/{id}")
    public List<Account> getAllDriverAccounts(@PathVariable Integer id) {
        Driver targetDriver = driverService.getById(id);
        return targetDriver != null ? targetDriver.getAccounts() : null;
    }

    @PostMapping("/dps/drivers/{id}")
    public ReMessage createAccount(@PathVariable Integer id) {
        ReMessage msg = new ReMessage();
        if (driverService.createAccount(id)) {
            msg.setMessage(SUCCESS_MSG);
        } else {
            msg.setMessage(FAIL_MSG);
        }
        return msg;
    }

    @DeleteMapping("/dps/drivers/{id}")
    public void deleteDriver(@PathVariable Integer id) {
        driverService.delete(id);
    }

}