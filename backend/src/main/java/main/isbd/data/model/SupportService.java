package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "\"Служба_поддержки\"")
public class SupportService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('\"Служба_поддержки_ид_seq\"'")
    @Column(name = "\"ид\"", nullable = false)
    private Integer id;

    @Column(name = "\"название\"", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "\"номер_телефона\"", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 64)
    private String email;

}