package morozov.ru.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import morozov.ru.model.Account;
import morozov.ru.model.Payment;
import morozov.ru.model.util.ReMessageBigDecimal;
import morozov.ru.service.serviceinterface.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Value("${payment.debit}")
    private String debitKey;
    @Value("${done.answer}")
    private String successMsg;
    @Value("${fail.answer}")
    private String failMsg;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;

    @Test
    public void whenSuccessInternalTransfer() throws Exception {
        ReMessageBigDecimal msg = new ReMessageBigDecimal();
        msg.setData(BigDecimal.valueOf(1.1));
        Mockito.when(accountService.internalTransfer(
                anyInt(),
                anyInt(),
                anyInt(),
                any(BigDecimal.class)
        ))
                .thenReturn(true);
        mockMvc.perform(post("/dps/accounts/1/1/2")
                .content(mapper.writeValueAsString(msg))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.data").value(successMsg));
    }

    @Test
    public void whenFailInternalTransfer() throws Exception {
        ReMessageBigDecimal msg = new ReMessageBigDecimal();
        msg.setData(BigDecimal.valueOf(1.1));
        Mockito.when(accountService.internalTransfer(
                anyInt(),
                anyInt(),
                anyInt(),
                any(BigDecimal.class)
        ))
                .thenReturn(false);
        mockMvc.perform(post("/dps/accounts/1/1/2")
                .content(mapper.writeValueAsString(msg))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.data").value(failMsg));
    }

    @Test
    public void whenGetBalance() throws Exception {
        Account account = new Account();
        account.setId(1);
        account.setBalance(BigDecimal.valueOf(1.1));
        Mockito.when(accountService.getById(any(Integer.class)))
                .thenReturn(account);
        mockMvc.perform(get("/dps/accounts/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.balance").value(1.1));
    }

    @Test
    public void whenSuccessTransfer() throws Exception {
        Payment payment = new Payment();
        payment.setOperation(debitKey);
        payment.setSum(BigDecimal.valueOf(1.1));
        Mockito.when(accountService.transfer(
                anyInt(),
                any(Payment.class)
        ))
                .thenReturn(true);
        mockMvc.perform(post("/dps/accounts/1")
                .content(mapper.writeValueAsString(payment))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.data").value(successMsg));
    }

    @Test
    public void whenFailTransfer() throws Exception {
        Payment payment = new Payment();
        payment.setOperation(debitKey);
        payment.setSum(BigDecimal.valueOf(1.1));
        Mockito.when(accountService.transfer(
                anyInt(),
                any(Payment.class)
        ))
                .thenReturn(false);
        mockMvc.perform(post("/dps/accounts/1")
                .content(mapper.writeValueAsString(payment))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.data").value(failMsg));
    }

}
