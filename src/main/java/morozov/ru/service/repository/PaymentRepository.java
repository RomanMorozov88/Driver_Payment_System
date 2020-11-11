package morozov.ru.service.repository;

import morozov.ru.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Calendar;

/**
 * Достать по дате:
 * https://www.baeldung.com/spring-data-jpa-query-by-date
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("SELECT sum(pmnt.sum) FROM Payment pmnt where pmnt.account.id = ?1 and pmnt.debit = ?2 and (pmnt.created between ?3 and ?4)")
    Double getTotalSum(int accountId, boolean debit, Calendar start, Calendar end);

}