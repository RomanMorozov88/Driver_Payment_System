package morozov.ru.model.util;

import java.math.BigDecimal;

/**
 * Модель для отчёта по приходам\расходам на счёте.
 */
public class Report {

    private BigDecimal debit;
    private BigDecimal credit;

    public Report() {
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }
}