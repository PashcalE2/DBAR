package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import main.isbd.data.users.Admin;
import main.isbd.data.users.Client;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "\"Заказ\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('\"Заказ_ид_seq\"'")
    @Column(name = "\"ид\"", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_клиента\"", nullable = false)
    private Client clientId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_консультанта\"", nullable = false)
    private Admin adminId;

    @Column(name = "\"поступил\"", nullable = false)
    private Instant createdAt;

    @Column(name = "\"завершен\"")
    private Instant completedAt;

    @Column(name = "\"итоговая_сумма\"", nullable = false)
    private Float sum;

    // TODO enum?
    @Column(name = "\"статус\"", columnDefinition = "\"Статус_заказа_enum\" not null")
    private String status;
}