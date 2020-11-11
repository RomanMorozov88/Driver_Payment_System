package morozov.ru.service.repository;

import morozov.ru.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account getById(int id);
    List<Account> getAccountsByOwnerId(int id);
}
