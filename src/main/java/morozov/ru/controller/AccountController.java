package morozov.ru.controller;

import morozov.ru.model.Payment;
import morozov.ru.model.util.ReMessageDouble;
import morozov.ru.model.util.ReMessageString;
import morozov.ru.service.serviceinterface.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final static String SUCCESS_MSG = "Done.";
    private final static String FAIL_MSG = "Something wrong.";

    @Autowired
    private AccountService accountService;

    @PostMapping("/dps/accounts/{owner}/{from}/{to}")
    public ReMessageString transfer(@PathVariable Integer owner,
                                    @PathVariable Integer from,
                                    @PathVariable Integer to,
                                    @RequestBody ReMessageDouble message) {
        ReMessageString msg = new ReMessageString();
        if (accountService.internalTransfer(owner, from, to, message.getData())) {
            msg.setData(SUCCESS_MSG);
        } else {
            msg.setData(FAIL_MSG);
        }
        return msg;
    }

    @PostMapping("/dps/accounts/{id}")
    public ReMessageString transfer(@PathVariable Integer id, @RequestBody Payment payment) {
        ReMessageString msg = new ReMessageString();
        if (accountService.transfer(id, payment)) {
            msg.setData(SUCCESS_MSG);
        } else {
            msg.setData(FAIL_MSG);
        }
        return msg;
    }

    @DeleteMapping("/dps/accounts/{id}")
    public void deleteAccount(@PathVariable Integer id) {
        accountService.delete(id);
    }
}
