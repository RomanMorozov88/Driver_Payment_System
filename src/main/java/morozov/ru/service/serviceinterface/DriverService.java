package morozov.ru.service.serviceinterface;

import morozov.ru.model.Driver;

import java.util.List;

public interface DriverService {

    List<Driver> getAll();

    Driver getById(int driverId);

    Driver save(Driver driver);

    void delete(int driverId);

    boolean createAccount(int driverID);
}
