package morozov.ru.service.serviceinterface;

import morozov.ru.model.Driver;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DriverService {

    List<Driver> getAll(Pageable pageable);

    Driver getById(int driverId);

    Driver save(Driver driver);

    void delete(int driverId);

    boolean createAccount(int driverID);
}
