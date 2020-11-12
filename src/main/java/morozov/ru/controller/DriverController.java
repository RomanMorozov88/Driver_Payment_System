package morozov.ru.controller;

import morozov.ru.model.Account;
import morozov.ru.model.Driver;
import morozov.ru.model.util.ReMessageString;
import morozov.ru.service.serviceinterface.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dps")
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

    @GetMapping("/drivers")
    public List<Driver> getAllDrivers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return driverService.getAll(pageable);
    }

    @PostMapping("/drivers")
    public Driver saveDriver(@RequestBody Driver driver) {
        Driver result = null;
        if (driver != null && driver.getName() != null) {
            result = driverService.save(driver);
        }
        return result;
    }

    @GetMapping("/drivers/{id}")
    public List<Account> getAllDriverAccounts(@PathVariable Integer id) {
        Driver targetDriver = driverService.getById(id);
        return targetDriver != null ? targetDriver.getAccounts() : null;
    }

    @PostMapping("/drivers/{id}")
    public ReMessageString createAccount(@PathVariable Integer id) {
        ReMessageString msg = new ReMessageString();
        if (driverService.createAccount(id)) {
            msg.setData(successMsg);
        } else {
            msg.setData(failMsg);
        }
        return msg;
    }

    @DeleteMapping("/drivers/{id}")
    public void deleteDriver(@PathVariable Integer id) {
        driverService.delete(id);
    }

}