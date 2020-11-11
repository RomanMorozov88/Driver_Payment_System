package morozov.ru.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean debit;
    private double sum;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account", nullable=false)
    private Account account;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created = new GregorianCalendar();

    public Payment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDebit() {
        return debit;
    }

    public void setDebit(boolean debit) {
        this.debit = debit;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }
}