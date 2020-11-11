package morozov.ru.controller;

import morozov.ru.model.Account;
import morozov.ru.model.Driver;
import morozov.ru.model.util.ReMessageString;
import morozov.ru.service.serviceinterface.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DriverController {

    @Value("${done.answer}")
    private String successMsg;
    @Value("${fail.answer}")
    private String failMsg;

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

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
    public ReMessageString createAccount(@PathVariable Integer id) {
        ReMessageString msg = new ReMessageString();
        if (driverService.createAccount(id)) {
            msg.setData(successMsg);
        } else {
            msg.setData(failMsg);
        }
        return msg;
    }

    @DeleteMapping("/dps/drivers/{id}")
    public void deleteDriver(@PathVariable Integer id) {
        driverService.delete(id);
    }

}