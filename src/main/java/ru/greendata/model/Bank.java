package ru.greendata.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@NamedQueries({
        @NamedQuery(name = Bank.DELETE, query = "DELETE FROM Bank b WHERE b.id=:id"),
        @NamedQuery(name = Bank.BY_BIC, query = "SELECT b FROM Bank b WHERE b.bic=?1"),
        @NamedQuery(name = Bank.ALL_SORTED, query = "SELECT b FROM Bank b ORDER BY b.name, b.bic"),
})

@Entity
@Table(name = "banks", uniqueConstraints = {@UniqueConstraint(columnNames = "bic", name = "banks_unique_bic_idx")})
public class Bank extends AbstractBaseEntity {

    public static final String DELETE = "Bank.delete";
    public static final String BY_BIC = "Bank.getByBic";
    public static final String ALL_SORTED = "Bank.getAllSorted";

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Size(min = 5, max = 15)
    @Column(name = "bic", nullable = false)
    private String bic;

    public Bank() {
    }

    public Bank(Bank b) {
        this(b.getId(), b.getName(), b.getBic());
    }

    public Bank(Integer id, String name, String bic) {
        super(id);
        this.name = name;
        this.bic = bic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bic=" + bic + '\'' +
                '}';
    }
}
