package morozov.ru.controller;

import morozov.ru.model.Account;
import morozov.ru.model.Payment;
import morozov.ru.model.util.ReMessageBigDecimal;
import morozov.ru.model.util.ReMessageString;
import morozov.ru.service.serviceinterface.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dps")
public class AccountController {

    @Value("${done.answer}")
    private String successMsg;
    @Value("${fail.answer}")
    private String failMsg;

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/accounts/{owner}/{from}/{to}")
    public ReMessageString doInternalTransfer(@PathVariable Integer owner,
                                              @PathVariable Integer from,
                                              @PathVariable Integer to,
                                              @RequestBody ReMessageBigDecimal message) {
        ReMessageString msg = new ReMessageString();
        if (accountService.internalTransfer(owner, from, to, message.getData())) {
            msg.setData(successMsg);
        } else {
            msg.setData(failMsg);
        }
        return msg;
    }

    @GetMapping("/accounts/{id}")
    public Account getBalance(@PathVariable Integer id) {
        return accountService.getById(id);
    }

    @PostMapping("/accounts/{id}")
    public ReMessageString doTransfer(@PathVariable Integer id, @RequestBody Payment payment) {
        ReMessageString msg = new ReMessageString();
        if (accountService.transfer(id, payment)) {
            msg.setData(successMsg);
        } else {
            msg.setData(failMsg);
        }
        return msg;
    }

    @DeleteMapping("/accounts/{id}")
    public void deleteAccount(@PathVariable Integer id) {
        accountService.delete(id);
    }
}
