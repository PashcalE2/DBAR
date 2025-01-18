package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "Клиент")
public class Client {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "номер_телефона", nullable = false, length = 20)
    private String phoneNumber;

    @Basic
    @Column(name = "email", nullable = false, length = 64)
    private String email;

    @Basic
    @Column(name = "пароль", nullable = false, length = 64)
    private String password;

    // TODO enum?
    @Column(name = "\"название\"", columnDefinition = "\"Доступные_организации_enum\" not null")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!Objects.equals(id, client.id)) return false;
        if (!Objects.equals(phoneNumber, client.phoneNumber))
            return false;
        if (!Objects.equals(email, client.email)) return false;
        if (!Objects.equals(password, client.password)) return false;
        if (!Objects.equals(name, client.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
