package ru.greendata.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NamedQueries({
        @NamedQuery(name = Client.DELETE, query = "DELETE FROM Client c WHERE c.id=:id"),
        @NamedQuery(name = Client.BY_NAME, query = "SELECT c FROM Client c WHERE c.name=?1"),
        @NamedQuery(name = Client.ALL_SORTED, query = "SELECT c FROM Client c ORDER BY c.name, c.address"),
})

@Entity
@Table(name = "clients")
public class Client extends AbstractBaseEntity {

    public static final String DELETE = "Client.delete";
    public static final String BY_NAME = "Client.getByName";
    public static final String ALL_SORTED = "Client.getAllSorted";

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Size(min = 2, max = 30)
    @Column(name = "shortName", nullable = false)
    private String shortName;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "address", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "form")
    private LegalForm form;

    public Client() {
    }

    public Client(Client c) {
        this(c.getId(), c.getName(), c.getShortName(), c.getAddress(), c.getForm());
    }

    public Client(Integer id, String name, String shortName, String address, LegalForm form) {
        super(id);
        this.name = name;
        this.shortName = shortName;
        this.address = address;
        this.form = form;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LegalForm getForm() {
        return form;
    }

    public void setForm(LegalForm form) {
        this.form = form;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", address='" + address + '\'' +
                ", form=" + form +
                '}';
    }
}
