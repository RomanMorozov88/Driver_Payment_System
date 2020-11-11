package morozov.ru.service.serviceimplements;

import morozov.ru.model.Account;
import morozov.ru.model.Driver;
import morozov.ru.service.repository.DriverRepository;
import morozov.ru.service.serviceinterface.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository repository;

    @Override
    public List<Driver> getAll() {
        return repository.findAll();
    }

    @Override
    public Driver getById(int driverId) {
        return repository.findById(driverId);
    }

    @Override
    public Driver save(Driver driver) {
        return repository.save(driver);
    }

    @Override
    public void delete(int driverId) {
        repository.deleteById(driverId);
    }

    @Override
    public boolean createAccount(int driverID) {
        boolean result = false;
        Driver targetDriver = repository.findById(driverID);
        if (targetDriver != null) {
            Account account = new Account();
            account.setOwner(targetDriver);
            targetDriver.setAccount(account);
            repository.save(targetDriver);
            result = true;
        }
        return result;
    }
}
