package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
@Entity
@Table(name = "Консультант")
public class Admin {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ид", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "ид_службы_поддержки", nullable = false)
    private Integer clientServiceId;

    @Basic
    @Column(name = "ид_расписания", nullable = false)
    private Integer scheduleId;

    @Basic
    @Column(name = "фио", nullable = false, length = 64)
    private String fullName;

    @Column(name = "логин", nullable = false, length = 64)
    private String login;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Admin admin = (Admin) o;

        if (!Objects.equals(id, admin.id)) return false;
        if (!Objects.equals(clientServiceId, admin.clientServiceId))
            return false;
        if (!Objects.equals(scheduleId, admin.scheduleId)) return false;
        if (!Objects.equals(fullName, admin.fullName)) return false;
        return Objects.equals(login, admin.login);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (clientServiceId != null ? clientServiceId.hashCode() : 0);
        result = 31 * result + (scheduleId != null ? scheduleId.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        return result;
    }
}
