package morozov.ru.service.serviceinterface;

import morozov.ru.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DriverService {

    Page<Driver> getAll(Pageable pageable);

    Driver getById(int driverId);

    Driver save(Driver driver);

    void delete(int driverId);

    boolean createAccount(int driverID);
}
