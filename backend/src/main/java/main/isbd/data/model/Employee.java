package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "\"Сотрудник\"")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('\"Сотрудник_ид_seq\"'")
    @Column(name = "\"ид\"", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_цеха\"", nullable = false)
    private Workshop workshopId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_расписания\"", nullable = false)
    private EmployeeSchedule scheduleId;

    @Column(name = "\"ФИО\"", nullable = false, length = 64)
    private String fullName;

    @Column(name = "\"номер_телефона\"", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 64)
    private String email;

    @Column(name = "\"пароль\"", nullable = false, length = 64)
    private String password;

    // TODO enum?
    @Column(name = "\"должность\"", columnDefinition = "\"Должность_enum\" not null")
    private String position;
}