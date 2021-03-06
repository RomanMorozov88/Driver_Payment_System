package morozov.ru.service.serviceimplements;

import morozov.ru.model.Account;
import morozov.ru.model.Driver;
import morozov.ru.service.repository.DriverRepository;
import morozov.ru.service.serviceinterface.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Override
    public List<Driver> getAll(Pageable pageable) {
        return driverRepository.findAll(pageable).getContent();
    }

    @Override
    public Driver getById(int driverId) {
        return driverRepository.findById(driverId);
    }

    @Override
    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public void delete(int driverId) {
        driverRepository.deleteById(driverId);
    }

    /**
     * Создаёт новый счёт для указанного водителя.
     * @param driverID
     * @return
     */
    @Override
    public boolean createAccount(int driverID) {
        boolean result = false;
        Driver targetDriver = driverRepository.findById(driverID);
        if (targetDriver != null) {
            Account account = new Account();
            account.setOwner(targetDriver);
            targetDriver.setAccount(account);
            driverRepository.save(targetDriver);
            result = true;
        }
        return result;
    }
}
