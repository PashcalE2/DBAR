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

    @Basic
    @Column(name = "номер_телефона", nullable = false, length = 20)
    private String phoneNumber;

    @Basic
    @Column(name = "email", nullable = false, length = 64)
    private String email;

    @Basic
    @Column(name = "пароль", nullable = false, length = 64)
    private String password;

    @Column(name = "адрес", nullable = false, length = Integer.MAX_VALUE)
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Factory factory = (Factory) o;

        if (!Objects.equals(id, factory.id)) return false;
        if (!Objects.equals(name, factory.name)) return false;
        if (!Objects.equals(phoneNumber, factory.phoneNumber))
            return false;
        if (!Objects.equals(email, factory.email)) return false;
        if (!Objects.equals(password, factory.password)) return false;
        if (!Objects.equals(address, factory.address)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
