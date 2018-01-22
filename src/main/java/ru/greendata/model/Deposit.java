package ru.greendata.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = Deposit.BY_BANK, query = "SELECT d FROM Deposit d WHERE d.bank.id=:bankId"),
        @NamedQuery(name = Deposit.BY_CLIENT, query = "SELECT d FROM Deposit d WHERE d.client.id=:clientId"),
        @NamedQuery(name = Deposit.DELETE, query = "DELETE FROM Deposit d WHERE d.id=:id AND d.bank.id=:bankId AND d.client.id=:clientId"),
        @NamedQuery(name = Deposit.ALL_SORTED, query = "SELECT d FROM Deposit d "),
})

@Entity
@Table(name = "deposits")
public class Deposit extends AbstractBaseEntity {

    public static final String BY_CLIENT = "Deposit.getByClient";
    public static final String BY_BANK = "Deposit.getByBank";
    public static final String DELETE = "Deposit.delete";
    public static final String ALL_SORTED = "Deposit.getAllSorted";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @NotNull
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", nullable = false)
    @NotNull
    private Bank bank;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "interest")
    @Range(min = 0, max = 1000)
    private double interest;

    @Column(name = "period")
    @Range(min = 1, max = 360)
    private int period;

    public Deposit() {
    }

    public Deposit(Deposit d) {
        this(d.getId(), d.getDate(), d.getInterest(), d.getPeriod());
    }

    public Deposit(Integer id, LocalDate date, double interest, int period) {
        super(id);
        this.date = date;
        this.interest = interest;
        this.period = period;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "id=" + id +
                ", date=" + date +
                ", interest=" + interest +
                ", period=" + period +
                '}';
    }
}
