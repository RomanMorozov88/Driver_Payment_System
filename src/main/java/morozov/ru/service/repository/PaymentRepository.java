package morozov.ru.service.repository;

import morozov.ru.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Calendar;
import java.util.List;

/**
 * Достать по дате:
 * https://www.baeldung.com/spring-data-jpa-query-by-date
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query(
            "SELECT sum(pmnt.sum) FROM Payment pmnt "
                    + "where pmnt.account.id = ?1 and pmnt.debit = ?2 "
                    + "and (pmnt.created between ?3 and ?4)")
    Double getTotalSum(int accountId, boolean debit, Calendar start, Calendar end);

    /**
     * Операции по счёту с accountId за период между start и end
     *
     * @param accountId
     * @param start
     * @param end
     * @return
     */
    @Query("FROM Payment pmnt where pmnt.account.id = ?1 and (pmnt.created between ?2 and ?3)")
    List<Payment> getPaymentsForPeriod(int accountId, Calendar start, Calendar end);

}
