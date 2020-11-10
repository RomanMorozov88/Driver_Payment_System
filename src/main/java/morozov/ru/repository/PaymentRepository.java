package morozov.ru.repository;

import morozov.ru.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Достать по дате:
 * https://www.baeldung.com/spring-data-jpa-query-by-date
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findAllByAccount_IdAndDebitAndCreatedBetween(int accountId, boolean isDebit, Date start, Date end);

    @Query("SELECT sum (pmnt.sum) FROM Payment pmnt where pmnt.account.id = ?1 and pmnt.debit = ?2 and pmnt.created between ?3 and ?4")
    Double getTotalSum(int accountId, boolean isDebit, Date start, Date end);

}
