package morozov.ru.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import morozov.ru.model.Account;
import morozov.ru.model.Driver;
import morozov.ru.service.serviceinterface.DriverService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(DriverController.class)
public class DriverControllerTest {

    @Value("${done.answer}")
    private String successMsg;
    @Value("${fail.answer}")
    private String failMsg;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DriverService driverService;

    @Test
    public void whenGetAllDrivers() throws Exception {
        Driver driver = new Driver();
        driver.setName("Test Driver");
        Pageable pageable = PageRequest.of(0, 5);
        Mockito.when(driverService.getAll(pageable))
                .thenReturn(new ArrayList<Driver>(Arrays.asList(driver)));
        mockMvc.perform(get("/dps/drivers")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(driver.getName()));
    }

    @Test
    public void whenSaveDriver() throws Exception {
        Driver driver = new Driver();
        driver.setName("Test Driver");
        Mockito.when(driverService.save(ArgumentMatchers.any(Driver.class)))
                .thenReturn(driver);
        mockMvc.perform(post("/dps/drivers")
                .content(mapper.writeValueAsString(driver))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(driver.getName()));
    }

    @Test
    public void whenGetAllDriverAccounts() throws Exception {
        Driver driver = new Driver();
        driver.setName("Test Driver");
        driver.setAccount(new Account());
        driver.setAccount(new Account());
        Mockito.when(driverService.getById(ArgumentMatchers.any(Integer.class)))
                .thenReturn(driver);
        mockMvc.perform(get("/dps/drivers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void whenSuccessCreateAccount() throws Exception {
        Mockito.when(driverService.createAccount(ArgumentMatchers.any(Integer.class)))
                .thenReturn(true);
        mockMvc.perform(post("/dps/drivers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.data").value(successMsg));
    }

    @Test
    public void whenFailCreateAccount() throws Exception {
        Mockito.when(driverService.createAccount(ArgumentMatchers.any(Integer.class)))
                .thenReturn(false);
        mockMvc.perform(post("/dps/drivers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.data").value(failMsg));
    }

}
