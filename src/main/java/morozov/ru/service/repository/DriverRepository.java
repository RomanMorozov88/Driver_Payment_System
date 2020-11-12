package morozov.ru.service.repository;

import morozov.ru.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    Driver findById(int id);

    void  deleteById(int id);

}