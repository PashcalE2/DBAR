package main.isbd.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import main.isbd.data.model.enums.SenderEnum;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "\"Сообщение\"")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('\"Сообщение_ид_seq\"'")
    @Column(name = "\"ид\"", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "\"ид_заказа\"", nullable = false)
    private Order orderId;

    @Column(name = "\"отправитель\"", columnDefinition = "\"Отправитель_enum\" not null")
    private SenderEnum sender;

    @Column(name = "\"текст\"", nullable = false, length = Integer.MAX_VALUE)
    private String text;

    @Column(name = "\"дата_время\"", nullable = false)
    private Timestamp sentAt;
}