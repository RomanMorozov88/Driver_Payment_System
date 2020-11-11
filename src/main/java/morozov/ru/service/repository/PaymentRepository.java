package morozov.ru.service.repository;

import morozov.ru.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Calendar;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    /**
     * Возвращает итоговую сумму всех операции по ключу operation за указанный период.
     *
     * @param accountId
     * @param operation
     * @param start
     * @param end
     * @return
     */
    @Query(
            "SELECT sum(pmnt.sum) FROM Payment pmnt "
                    + "where pmnt.account.id = ?1 and pmnt.operation = ?2 "
                    + "and (pmnt.created between ?3 and ?4)")
    BigDecimal getTotalSum(int accountId, String operation, Calendar start, Calendar end);

    /**
     * Операции по счёту с accountId за период между start и end
     *
     * @param accountId
     * @param start
     * @param end
     * @return
     */
    @Query("FROM Payment pmnt where pmnt.account.id = ?1 and (pmnt.created between ?2 and ?3)")
    Page<Payment> getPaymentsForPeriod(int accountId, Calendar start, Calendar end, Pageable pageable);

}
