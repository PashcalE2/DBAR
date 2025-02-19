package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import main.isbd.data.model.enums.OrderStatusEnum;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "заказ")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('заказ_ид_seq')")
    @Column(name = "ид", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ид_клиента", nullable = false)
    private Client clientId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ид_консультанта", nullable = false)
    private Admin adminId;

    @Column(name = "статус", columnDefinition = "статус_заказа_enum not null")
    private OrderStatusEnum status;

    @Column(name = "поступил", nullable = false)
    private Timestamp createdAt;

    @Column(name = "завершен")
    private Timestamp completedAt;
}