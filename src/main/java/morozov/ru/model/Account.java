package morozov.ru.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double  balance;
    @ManyToOne
    @JoinColumn(name="owner", nullable=false)
    private Driver owner;
//    @OneToMany(mappedBy="account")
//    private List<Payment> payments;

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Driver getOwner() {
        return owner;
    }

    public void setOwner(Driver owner) {
        this.owner = owner;
    }

//    public List<Payment> getPayments() {
//        return payments;
//    }
//
//    public void setPayments(List<Payment> payments) {
//        this.payments = payments;
//    }
}
