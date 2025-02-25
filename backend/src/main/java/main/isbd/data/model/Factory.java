package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "Завод")
public class Factory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "название", nullable = false, length = -1)
    private String name;

    @Column(name = "адрес", nullable = false, length = Integer.MAX_VALUE)
    private String address;

    @Column(name = "логин", nullable = false, length = 64)
    private String login;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Factory factory = (Factory) o;

        if (!Objects.equals(id, factory.id)) return false;
        if (!Objects.equals(name, factory.name)) return false;
        if (!Objects.equals(login, factory.login)) return false;
        return Objects.equals(address, factory.address);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
