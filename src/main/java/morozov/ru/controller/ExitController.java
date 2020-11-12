package morozov.ru.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dps")
public class ExitController {

    @Autowired
    private ApplicationContext context;

    @GetMapping("/exit")
    public void getExit() {
        SpringApplication.exit(context, () -> 0);
    }

}
