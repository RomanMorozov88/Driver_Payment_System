package morozov.ru.service.repository;

import morozov.ru.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account getById(int id);
    void deleteById(int accountId);
}
