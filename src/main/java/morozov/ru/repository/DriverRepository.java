package morozov.ru.repository;

import morozov.ru.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Integer> {

    Driver findById(int id);
    void  deleteById(int id);

}